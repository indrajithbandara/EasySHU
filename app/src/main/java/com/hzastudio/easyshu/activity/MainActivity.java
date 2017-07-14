package com.hzastudio.easyshu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseTablePageAdapter;
import com.hzastudio.easyshu.fragment.CourseTableDayFragment;
import com.hzastudio.easyshu.support.universal.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
                                                          View.OnClickListener{

    private SwipeRefreshLayout refresh;
    private CourseTablePageAdapter pageAdapter;
    private ViewPager viewPager;

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
        pageAdapter=new CourseTablePageAdapter(getSupportFragmentManager(),mFragments);
        viewPager=(ViewPager)findViewById(R.id.CourseTableSlider);
        viewPager.setAdapter(pageAdapter);
        /*防止swiperefresh和viewpager冲突*/
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
        /*viewpager多页切换组件*********************END************/

        /*SwipeRefreshLayout下拉刷新组件************START**********/
        refresh=(SwipeRefreshLayout)findViewById(R.id.CourseTableRefresh);
        refresh.setOnRefreshListener(this);
        /*SwipeRefreshLayout下拉刷新组件************END************/

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {

    }
    
}
