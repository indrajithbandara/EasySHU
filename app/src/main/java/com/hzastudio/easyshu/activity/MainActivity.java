package com.hzastudio.easyshu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseTablePageAdapter;
import com.hzastudio.easyshu.fragment.CourseTableDayFragment;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.task.Task_CheckIfUserExist;
import com.hzastudio.easyshu.ui.widget.ViewPagerSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.NewThreadScheduler;

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
                Boolean res=new Task_CheckIfUserExist().execute().get();
                if(res)
                {
                    e.onNext("USER OK!");
                }
                else
                {
                    e.onNext("ERR!");
                }
                e.onComplete();
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
