package com.hzastudio.easyshu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseTablePageAdapter;
import com.hzastudio.easyshu.fragment.CourseTableDayFragment;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.task.Task_AcquirePublicKey;
import com.hzastudio.easyshu.task.Task_CheckIfUserExist;
import com.hzastudio.easyshu.task.Task_CreateNewUser;
import com.hzastudio.easyshu.task.Task_DeleteUserInfo;
import com.hzastudio.easyshu.ui.widget.ViewPagerSwipeRefreshLayout;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
                                                          View.OnClickListener{

    private ViewPagerSwipeRefreshLayout refresh;
    private CourseTablePageAdapter pageAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*viewpager多页切换组件*********************START**********/
        List<Fragment> mFragments=new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            mFragments.add(new CourseTableDayFragment());
        }
        List<String> mTitles=new ArrayList<>();
        mTitles.add(0,"一");
        mTitles.add(1,"二");
        mTitles.add(2,"三");
        mTitles.add(3,"四");
        mTitles.add(4,"五");
        pageAdapter=new CourseTablePageAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPager=(ViewPager)findViewById(R.id.CourseTableSlider);
        viewPager.setAdapter(pageAdapter);
        /*防止swiperefresh和viewpager冲突*/
        /*
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        refresh.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        refresh.setEnabled(true);
                        break;
                    default:break;
                }
                return false;
            }
        });
        */
        /*ViewPager多页切换组件*********************END************/

        /*TabLayout标签切换组件*******************START************/
        tabs = (TabLayout) findViewById(R.id.CourseTableTab);
        tabs.setupWithViewPager(viewPager);
        /*TabLayout标签切换组件*******************END**************/

        /*SwipeRefreshLayout下拉刷新组件************START**********/
        refresh=(ViewPagerSwipeRefreshLayout)findViewById(R.id.CourseTableRefresh);
        refresh.setOnRefreshListener(this);
        refresh.setProgressViewOffset(false,200,250);
        /*SwipeRefreshLayout下拉刷新组件************END************/

    }

    @Override
    public void onRefresh() {
        Observable<String> observable=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String psw="Phantom888";

        /* md5 */
                MessageDigest md5=MessageDigest.getInstance("MD5");
                md5.update(psw.getBytes());
                byte[] m=md5.digest();
                StringBuilder ret=new StringBuilder(m.length<<1);
                for (byte aM : m) {
                    ret.append(Character.forDigit((aM >> 4) & 0xf, 16));
                    ret.append(Character.forDigit(aM & 0xf, 16));
                }
                String md5psw=Base64.encodeToString(ret.toString().getBytes(),Base64.DEFAULT);

        /* publicKey Encrypt */
                if(new Task_AcquirePublicKey().execute("16121683").get()) {
                    SharedPreferences sp = MainApplication.getContext().getSharedPreferences("data",
                            Context.MODE_PRIVATE);
                    String publicKey = sp.getString("publicKey", null);
                    Log.d("sss","PublicKey:"+publicKey);
                    byte[] decoded = Base64.decode(publicKey, Base64.DEFAULT);
                    X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
                    KeyFactory factory = KeyFactory.getInstance("RSA");
                    PublicKey key = factory.generatePublic(spec);
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    byte[] encrypted = cipher.doFinal(psw.getBytes());
                    String result = Base64.encodeToString(encrypted, Base64.DEFAULT);
                    Log.d("sss","Encoded Password:"+result);
                    Boolean res = new Task_CreateNewUser().execute("16121683", result, md5psw).get();
                    Log.d("sss", "Result:" + res);
                    e.onComplete();
                }
                else
                {
                    e.onError(new Throwable("23333"));
                }
            }
        });
        Observer<String> observer=new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d("sss",s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                refresh.setRefreshing(false);
            }
        };
        observable.subscribeOn(new NewThreadScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void onClick(View v) {

    }

}
