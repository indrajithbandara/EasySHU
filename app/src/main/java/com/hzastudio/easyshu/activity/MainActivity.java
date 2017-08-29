package com.hzastudio.easyshu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseTablePageAdapter;
import com.hzastudio.easyshu.fragment.CourseTableDayFragment;
import com.hzastudio.easyshu.module.CJSystemHandler;
import com.hzastudio.easyshu.support.data_bean.CurrentCourse;
import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.support.data_bean.UserCourse;
import com.hzastudio.easyshu.support.program_const.CourseStatus;
import com.hzastudio.easyshu.support.tool.CourseProcessor;
import com.hzastudio.easyshu.support.universal.ActivityCollector;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.ui.widget.ViewPagerSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
                                                          View.OnClickListener,
                                                          NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar CourseTableToolBar;
    private ViewPagerSwipeRefreshLayout SwipeRefresh;
    private CourseTablePageAdapter pageAdapter;
    private ViewPager viewPager;
    private TabLayout tabs;
    private DrawerLayout CourseTableDrawerLayout;
    private FloatingActionButton CourseTableFloatingButton;

    private boolean NavigationSelectedFlag=false;
    private long BackPressTimer=0;

    private List<UserCourse> userCourses=new ArrayList<>();
    private List<List<TableCourse>> tableCourses=new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Toolbar标题栏控件*************************START**********/
        CourseTableToolBar = (Toolbar) findViewById(R.id.CourseTableToolbar);
        setSupportActionBar(CourseTableToolBar);
        ActionBar CourseTableActionBar=getSupportActionBar();
        if(CourseTableActionBar!=null)
        {
            CourseTableActionBar.setDisplayHomeAsUpEnabled(true);
            CourseTableActionBar.setHomeAsUpIndicator(R.mipmap.menu);
        }
        /*Toolbar标题栏控件*************************END************/

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
        /*ViewPager多页切换组件*********************END************/

        /*TabLayout标签切换组件*******************START************/
        tabs = (TabLayout) findViewById(R.id.CourseTableTab);
        tabs.setupWithViewPager(viewPager);
        /*TabLayout标签切换组件*******************END**************/

        /*SwipeRefreshLayout下拉刷新组件************START**********/
        SwipeRefresh =(ViewPagerSwipeRefreshLayout)findViewById(R.id.CourseTableRefresh);
        SwipeRefresh.setOnRefreshListener(this);
        SwipeRefresh.setProgressViewOffset(false,200,250);
        /*SwipeRefreshLayout下拉刷新组件************END************/

        /*DrawerLayout滑动抽屉组件****************START************/
        CourseTableDrawerLayout=(DrawerLayout)findViewById(R.id.CourseTableDrawerLayout);
        /*DrawerLayout滑动抽屉组件****************END**************/

        /*NavigationView导航栏组件***************START************/
        NavigationView navigationView=(NavigationView)findViewById(R.id.CourseTableNavigationView);
        navigationView.setNavigationItemSelectedListener(this);
        /*NavigationView导航栏组件***************END**************/

        /*FloatButton悬浮按钮组件*****************START***********/
        CourseTableFloatingButton = (FloatingActionButton)
                findViewById(R.id.CourseTableFloatingButton);
        CourseTableFloatingButton.setOnClickListener(this);
        /*FloatButton悬浮按钮组件*****************END*************/

        /*启动逻辑*******************************START***********/

        /*首先检查是否有保存课表*/
        SharedPreferences SavedCourseTable = MainApplication.getContext()
                .getSharedPreferences("course_table",MODE_PRIVATE);
        if(SavedCourseTable.getAll().size()!=0) {
            //有：加载课表
            userCourses = new ArrayList<>();
            int count = 1;
            while (count <= SavedCourseTable.getAll().size() / 9) {
                UserCourse course = new UserCourse();
                course.setCourseNum(SavedCourseTable.getString("CourseNum" + count, ""));
                course.setCourseName(SavedCourseTable.getString("CourseName" + count, ""));
                course.setTeacherNum(SavedCourseTable.getString("TeacherNum" + count, ""));
                course.setTeacherName(SavedCourseTable.getString("TeacherName" + count, ""));
                course.setCourseTime(SavedCourseTable.getString("CourseTime" + count, ""));
                course.setCourseRoom(SavedCourseTable.getString("CourseRoom" + count, ""));
                course.setCourseQuesTime(SavedCourseTable.getString("CourseQuesTime" + count, ""));
                course.setCourseQuesPlace(SavedCourseTable.getString("CourseQuesPlace" + count, ""));
                course.setCourseTimeDetail(SavedCourseTable.getString("CourseTimeDetail" + count, ""));
                userCourses.add(course);
                count++;
            }
            tableCourses = CourseProcessor.ClassifyToTableCourseList(userCourses);
            int i=0;
            for(List<TableCourse> list : tableCourses)
            {
                ((CourseTableDayFragment)pageAdapter.getItem(i)).setCourseList(list);
                i++;
            }
        }
        else
        {
            //无，刷新课表
            SwipeRefresh.setRefreshing(true);
            RefreshCourseTable();
        }

        /*获取传入的数据****************************START**********/
        Intent data = this.getIntent();
        //传入true说明要刷新课表(学期更新)
        if(data.getBooleanExtra("data",false))
        {
            SwipeRefresh.setRefreshing(true);
            RefreshCourseTable();
        }
        /*获取传入的数据****************************END************/

        if(userCourses.size()!=0) {
            //定位课表
            //等待加载完成
            Thread Wait = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (((CourseTableDayFragment) pageAdapter.getItem(0)).getCourseView() == null) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            Wait.start();
            try {
                Wait.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取当前课程的定位以及状态信息
            CurrentCourse course = CourseProcessor.GetCurrentCoursePos(userCourses);
            //Log.d("MainActivity","CourseTime:"+course.getCurrentCourseTime());
            //Log.d("MainActivity","CourseWeekday:"+course.getCurrentCourseWeekday());
            //Log.d("MainActivity","CourseStatus:"+course.getCurrentCourseStatus());
            //显示当前课程指示器
            for (List<TableCourse> tmp1 : tableCourses) {
                for (TableCourse tmp2 : tmp1) {
                    if (tmp2 != null) tmp2.setCourseIsCurrent(false);
                }
            }
            if (course.getCurrentCourseStatus() != CourseStatus.STATUS_NULL &&
                    course.getCurrentCourseStatus() != CourseStatus.STATUS_NOT_COURSE_TIME) {
                tableCourses.get(course.getCurrentCourseWeekday() - 1)
                        .get(course.getCurrentCourseTime())
                        .setCourseIsCurrent(true);
            }

            //刷新课表
            for (int i = 0; i < 5; i++) {
                ((CourseTableDayFragment) pageAdapter.getItem(i)).setCourseList(tableCourses.get(i));
            }

            if (course.getCurrentCourseStatus() != CourseStatus.STATUS_NULL &&
                    course.getCurrentCourseStatus() != CourseStatus.STATUS_NOT_COURSE_TIME) {
                //移动到当前课程的星期
                tabs.getTabAt(course.getCurrentCourseWeekday() - 1).select();
                //移动到当前课程的时间
                CourseTableDayFragment fragment = (CourseTableDayFragment)
                        pageAdapter.getItem(course.getCurrentCourseWeekday() - 1);
                fragment.CourseViewScrollTo(course.getCurrentCourseTime());
            }
        }

        /*启动逻辑*******************************END*************/

    }

    @Override
    public void onRefresh() {
        RefreshCourseTable();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.CourseTableFloatingButton:
                if(SwipeRefresh.isRefreshing()||userCourses==null||tableCourses==null)break;
                //获取当前课程的定位以及状态信息
                CurrentCourse course = CourseProcessor.GetCurrentCoursePos(userCourses);
                //Log.d("MainActivity","CourseTime:"+course.getCurrentCourseTime());
                //Log.d("MainActivity","CourseWeekday:"+course.getCurrentCourseWeekday());
                //Log.d("MainActivity","CourseStatus:"+course.getCurrentCourseStatus());
                //显示当前课程指示器
                for(List<TableCourse> tmp1 : tableCourses)
                {
                    for (TableCourse tmp2 : tmp1)
                    {
                        if(tmp2!=null) tmp2.setCourseIsCurrent(false);
                    }
                }
                if(course.getCurrentCourseStatus()!= CourseStatus.STATUS_NULL &&
                        course.getCurrentCourseStatus()!= CourseStatus.STATUS_NOT_COURSE_TIME)
                {
                    tableCourses.get(course.getCurrentCourseWeekday()-1)
                            .get(course.getCurrentCourseTime())
                            .setCourseIsCurrent(true);
                }

                //刷新课表
                for(int i=0;i<5;i++)
                {
                    ((CourseTableDayFragment)pageAdapter.getItem(i)).setCourseList(tableCourses.get(i));
                }

                if(course.getCurrentCourseStatus()!= CourseStatus.STATUS_NULL &&
                        course.getCurrentCourseStatus()!=CourseStatus.STATUS_NOT_COURSE_TIME)
                {
                    //移动到当前课程的星期
                    tabs.getTabAt(course.getCurrentCourseWeekday()-1).select();
                    //移动到当前课程的时间
                    CourseTableDayFragment fragment =(CourseTableDayFragment)
                            pageAdapter.getItem(course.getCurrentCourseWeekday()-1);
                    fragment.CourseViewScrollTo(course.getCurrentCourseTime());
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                CourseTableDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //两次按返回键间隔一秒有效
        if(System.currentTimeMillis()-BackPressTimer<1000)
        {
            ActivityCollector.FinishAllActivities();
            return;
        }
        BackPressTimer=System.currentTimeMillis();
        Snackbar.make(CourseTableFloatingButton,"再按一次返回键回到桌面",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        //等到滑动抽屉完全关闭后再打开新活动
        CourseTableDrawerLayout.closeDrawers();
        NavigationSelectedFlag=true;
        CourseTableDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if(newState==DrawerLayout.STATE_IDLE && NavigationSelectedFlag){
                    NavigationSelectedFlag=false;
                    CourseTableDrawerLayout.removeDrawerListener(this);
                    switch (item.getItemId())
                    {
                        case R.id.Navigation_CourseQuery:
                            StartNewActivity(MainActivity.this,CourseQueryActivity.class);
                            break;
                        default:break;
                    }
                }
            }
        });
        return false;
    }

    ////////////工具函数/////////////
    /*刷新课表*/
    private void RefreshCourseTable() {
        CJSystemHandler.GetCourseTable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TableCourse>>() {
            int i=0;
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull List<TableCourse> tableCourses) {
                ((CourseTableDayFragment)pageAdapter.getItem(i)).setCourseList(tableCourses);
                i++;
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                SwipeRefresh.setRefreshing(false);
            }
        });
    }
    ////////////////////////////////
}
