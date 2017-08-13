package com.hzastudio.easyshu.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseTablePageAdapter;
import com.hzastudio.easyshu.fragment.CourseTableDayFragment;
import com.hzastudio.easyshu.module.UserConfig;
import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.support.data_bean.TimeYearAndSeason;
import com.hzastudio.easyshu.support.data_bean.UserCourse;
import com.hzastudio.easyshu.support.tool.CourseClassify;
import com.hzastudio.easyshu.support.tool.HttpFramework;
import com.hzastudio.easyshu.support.universal.ActivityCollector;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.task.CJTasks;
import com.hzastudio.easyshu.task.TimeTasks;
import com.hzastudio.easyshu.ui.widget.ViewPagerSwipeRefreshLayout;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

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

    private boolean NavigationSelectedFlag=false;

    private List<UserCourse> userCourses=new ArrayList<>();

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
        FloatingActionButton CourseTableFloatingButton = (FloatingActionButton)
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
            List<List<TableCourse>> result = CourseClassify.ClassifyToTableCourseList(userCourses);
            int i=0;
            for(List<TableCourse> list : result)
            {
                ((CourseTableDayFragment)pageAdapter.getItem(i)).setCourseList(list);
                i++;
            }
        }
        else
        {
            //没有：检查本地账号
            
            RefreshCourseTable();
        }


        /*启动逻辑*******************************END*************/

    }

    @Override
    public void onRefresh() {
        RefreshCourseTable();
    }

    @Override
    public void onClick(View v) {
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
        ActivityCollector.FinishAllActivities();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                }
            }
        });
        return false;
    }

    private void RefreshCourseTable() {
        Observable.create(new ObservableOnSubscribe<List<TableCourse>>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<TableCourse>> e) throws Exception {
                TimeYearAndSeason time = TimeTasks.Task_GetYearAndSeason();
                String year=time.getTermYear();
                String season="";
                switch (time.getTermSeason()) {
                    case "春":
                        season = "3";
                        break;
                    case "夏":
                        season = "5";
                        break;
                    case "秋":
                        season = "1";
                        break;
                    case "冬":
                        season = "2";
                        break;
                    default:
                        break;
                }
                List<UserCourse> courseList = CJTasks.Task_CJ_getCourseTable("16121683",year,season);
                //////////////////保存课表
                int Count=1;
                SharedPreferences preferences=getSharedPreferences("course_table",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                for(UserCourse a:courseList){
                    editor.putString("CourseNum"+Count,a.getCourseNum());
                    editor.putString("CourseName"+Count,a.getCourseName());
                    editor.putString("TeacherNum"+Count,a.getTeacherNum());
                    editor.putString("TeacherName"+Count,a.getTeacherName());
                    editor.putString("CourseTime"+Count,a.getCourseTime());
                    editor.putString("CourseRoom"+Count,a.getCourseRoom());
                    editor.putString("CourseQuesTime"+Count,a.getCourseQuesTime());
                    editor.putString("CourseQuesPlace"+Count,a.getCourseQuesPlace());
                    editor.putString("CourseTimeDetail"+Count,a.getCourseTimeDetail());
                    Count++;
                }
                editor.apply();
                /////////////////////////
                List<List<TableCourse>> result = CourseClassify.ClassifyToTableCourseList(courseList);
                for(List<TableCourse> list : result)
                {
                    e.onNext(list);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
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

}
