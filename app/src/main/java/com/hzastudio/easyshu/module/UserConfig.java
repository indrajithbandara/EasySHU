package com.hzastudio.easyshu.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.hzastudio.easyshu.support.program_const.URL;
import com.hzastudio.easyshu.support.tool.SecurityTool;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.task.UserTasks;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.hzastudio.easyshu.support.tool.SecurityTool.MD5;
import static com.hzastudio.easyshu.support.tool.SecurityTool.RSA_EncryptedWithPublicKey;

public class UserConfig {

    //TODO:尚未DEBUG，使用时需关注问题

    public static Observable<Boolean> CheckServer()
    {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                OkHttpClient client=new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS)
                        .writeTimeout(2, TimeUnit.SECONDS)
                        .readTimeout(2, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url(URL.SERVER_CHECK_URL)
                        .build();
                String result = client.newCall(request).execute().body().string();
                Log.d("CheckServer",result);
                if(!result.equals("OK"))
                {
                    e.onError(new Throwable("网络错误"));
                }
                else
                {
                    Log.d("CheckServer","服务器连接正常！");
                    e.onNext(true);
                    e.onComplete();
                }
            }
        });
    }

    /**
     * 创建用户
     * @param Username 用户名
     * @param Password 密码
     * @return Observable对象，用于rxjava2观察者模式
     */
    public static Observable<Boolean> CreateUser(final String Username, final String Password)
    {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                /*检查本地是否已经存在该用户*/
                SharedPreferences sp= MainApplication.getContext()
                        .getSharedPreferences("user", Context.MODE_PRIVATE);
                if(sp.getString("username","").equals(Username) &&
                        sp.getString("password","").equals(Base64.encodeToString(MD5(MD5(Password)).getBytes(),
                                Base64.DEFAULT)))
                {
                    Log.d("CreateUser","本地已有该用户!");
                    e.onNext(true);
                    e.onComplete();//存在则截断事件
                }
                else//不存在则继续事件
                {
                    //删除服务器上的账户
                    String DeleteResult = UserTasks.Task_DeleteUser(Username,
                            Base64.encodeToString(MD5(MD5(Password)).getBytes(),Base64.DEFAULT));
                    if(DeleteResult.equals("AUTH NG"))
                    {
                        e.onError(new Throwable("删除错误"));
                    }
                    else
                    {
                        Log.d("CreateUser","服务器账户清除成功！");
                        Boolean KeyResult= UserTasks.Task_GetKey(Username);
                        if(!KeyResult)
                        {
                            e.onError(new Throwable("未知错误"));
                        }
                        else
                        {
                            Log.d("CreateUser","密钥获取成功！");
                            String CreateResult = UserTasks.Task_CreateUser(Username,
                                    SecurityTool.RSA_EncryptedWithPublicKey(Password),
                                    Base64.encodeToString(MD5(MD5(Password)).getBytes(),
                                            Base64.DEFAULT));
                            switch (CreateResult) {
                                case "USRPSW NG":
                                    e.onError(new Throwable("账号或密码错误"));
                                    break;
                                case "OK":
                                    Log.d("CreateUser","创建账户成功！");
                                    sp.edit().putString("username", Username)
                                            .putString("password", MD5(MD5(Password)))
                                            .apply();
                                    Log.d("CreateUser","数据写入成功！");
                                    e.onNext(true);
                                    e.onComplete();
                                    break;
                                default:
                                    e.onError(new Throwable("未知错误"));
                                    break;
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 删除用户
     * @return Observable对象，用于rxjava2观察者模式
     */
    public static Observable<Boolean> DeleteCurrentUser()
    {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                SharedPreferences sp = MainApplication.getContext().
                        getSharedPreferences("user",Context.MODE_PRIVATE);
                String Username=sp.getString("username","");
                String Password=sp.getString("password","");
                String DeleteResult = UserTasks.Task_DeleteUser(Username,
                            Base64.encodeToString(Password.getBytes(), Base64.DEFAULT));
                if (DeleteResult.equals("AUTH NG")) {
                    e.onError(new Throwable("删除错误"));
                } else {
                    e.onNext(true);
                    e.onComplete();
                }
            }
        });

    }

    /**
     * 改变密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return Observable对象，用于rxjava2观察者模式
     */
    public static Observable<Boolean> ChangeUserPassword(final String oldPassword,final String newPassword)
    {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                SharedPreferences sp = MainApplication.getContext().
                        getSharedPreferences("user",Context.MODE_PRIVATE);

                String ConfirmPassword=sp.getString("password","");
                if(!MD5(MD5(oldPassword)).equals(ConfirmPassword))
                {
                    e.onError(new Throwable("旧密码错误"));
                }
                else {
                    String ChangeResult =
                            UserTasks.Task_ChangeUserPassword(sp.getString("username", ""),
                            RSA_EncryptedWithPublicKey(oldPassword),
                            Base64.encodeToString(ConfirmPassword.getBytes(), Base64.DEFAULT),
                            RSA_EncryptedWithPublicKey(newPassword),
                            Base64.encodeToString(MD5(MD5(newPassword)).getBytes(), Base64.DEFAULT)
                            );
                    switch (ChangeResult) {
                        case "USER NG":
                        case "OLDPSWCONF NG":
                        case "OLDPSW NG":
                        case "NEWPSWCONF NG":
                            e.onError(new Throwable("未知错误"));
                            break;
                        case "NEWPSW NG":
                            e.onError(new Throwable("新密码错误"));
                            break;
                        default:
                            e.onNext(true);
                            e.onComplete();
                            break;
                    }
                }
            }
        });
    }

}
