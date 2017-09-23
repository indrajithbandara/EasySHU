package com.hzastudio.easyshu.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.data_bean.TermTime;
import com.hzastudio.easyshu.support.universal.ActivityCollector;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.task.TimeTasks;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 启动界面
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class LaunchActivity extends BaseActivity {

    SharedPreferences usr,app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        usr = MainApplication.getContext().getSharedPreferences("user",MODE_PRIVATE);
        app = MainApplication.getContext().getSharedPreferences("application", MODE_PRIVATE);

        Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Void> e) throws Exception {
                boolean RefreshingCourse = false;
                //检查本地是否有校历
                if(app.getString("year",null)==null||
                        app.getString("season",null)==null||
                        app.getString("calendar",null)==null)
                {
                    //获取校历
                    TermTime term = TimeTasks.Task_GetTermTime();
                    String season = null;
                    switch (term.getTermSeason())
                    {
                        case "秋":season="1";break;
                        case "冬":season="2";break;
                        case "春":season="3";break;
                        case "夏":season="5";break;
                        default:break;
                    }
                    app.edit().putString("year",term.getTermYear())
                            .putString("season",season)
                            .putString("calendar",term.getCalendar())
                            .apply();
                    RefreshingCourse=true;
                }
                else
                {
                    //检查校历是否过期
                    String Calendar = app.getString("calendar",null);
                    Log.d("LaunchActivity","Calendar:"+Calendar);
                    String[] week = Calendar.split("\\|");
                    String SpecWeek = week[week.length-1];
                    Log.d("LaunchActivity","SpecWeek:"+SpecWeek);
                    String[] Day = SpecWeek.split(",");
                    String SpecDay = Day[Day.length-1];
                    Log.d("LaunchActivity","SpecDay:"+SpecDay);
                    String[] DayDetail = SpecDay.split("/");
                    java.util.Calendar calendar= java.util.Calendar.getInstance();
                    Log.d("LaunchActivity","Year:"+Integer.parseInt(DayDetail[0]));
                    Log.d("LaunchActivity","Month:"+Integer.parseInt(DayDetail[1]));
                    Log.d("LaunchActivity","Day:"+Integer.parseInt(DayDetail[2]));
                    calendar.set(Integer.parseInt(DayDetail[0]),
                            Integer.parseInt(DayDetail[1])-1,
                            Integer.parseInt(DayDetail[2]),
                            23,59,59);
                    long TimeLast = calendar.getTimeInMillis();
                    long TimeNow = System.currentTimeMillis();
                    Log.d("LaunchActivity","Now:"+TimeNow);
                    Log.d("LaunchActivity","Last:"+TimeLast);
                    if (TimeNow>=TimeLast)
                    {
                        if(!isNetworkConnected(MainApplication.getContext()))
                        {
                            throw new Exception("NET NG");
                        }
                        //校历已经过期，获取校历
                        TermTime term = TimeTasks.Task_GetTermTime();
                        app.edit().putString("year",term.getTermYear())
                                .putString("season",term.getTermSeason())
                                .putString("calendar",term.getCalendar())
                                .apply();
                        RefreshingCourse=true;
                    }
                }

                if(usr.getString("username", null) == null || usr.getString("password", null) == null) {
                    usr.edit().remove("username").remove("password").apply();//删除用户可能因为程序错误留下的数据
                    StartNewActivity(LaunchActivity.this, LoginActivity.class,RefreshingCourse);
                }
                else
                {
                    //异常情况：欸？第一次启动本地竟然有数据！？（卸载残留吧这是）
                    if(app.getBoolean("firstLaunch",true))
                    {
                        app.edit().putBoolean("firstLaunch",false).apply();
                        new AlertDialog.Builder(LaunchActivity.this)
                                .setMessage("检测到本地已有账户:" +
                                        usr.getString("username", null) +
                                        "\n是否为您的账户？")
                                .setCancelable(false)
                                .setPositiveButton("是的，继续使用", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        StartNewActivity(LaunchActivity.this, MainActivity.class,false);
                                    }
                                })
                                .setNegativeButton("不是，我要修改", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        usr.edit().remove("username").remove("password").apply();//删除用户可能因为程序错误留下的数据
                                        StartNewActivity(LaunchActivity.this, LoginActivity.class,false);
                                    }
                                })
                                .show();
                    }
                    else
                    {
                        StartNewActivity(LaunchActivity.this, MainActivity.class,false);
                    }
                }
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Void>() {
                               @Override
                               public void accept(@NonNull Void aVoid) throws Exception {

                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Log.e("LaunchActivity","Error:"+throwable.getMessage());
                                switch (throwable.getMessage())
                                {
                                    case "NET NG":
                                        Toast.makeText(MainApplication.getContext(),"校历不存在或已过期，请连接网络获取！",Toast.LENGTH_SHORT).show();
                                        ActivityCollector.FinishAllActivities();
                                        break;
                                    default:
                                        Toast.makeText(MainApplication.getContext(),"发生未知错误，请检查网络！",Toast.LENGTH_SHORT).show();
                                        ActivityCollector.FinishAllActivities();
                                }
                            }
                        });

    }

    /**
     * 网络是否可用
     * @param context 上下文
     * @return 网络状态
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
