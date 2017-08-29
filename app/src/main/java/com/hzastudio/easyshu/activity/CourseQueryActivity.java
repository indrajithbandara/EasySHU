package com.hzastudio.easyshu.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseQueryRecyclerViewAdapter;
import com.hzastudio.easyshu.support.data_bean.CourseQueryCourse;
import com.hzastudio.easyshu.support.program_const.CourseStatus;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.ui.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

public class CourseQueryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private MenuItem FilterIcon;

    private int CollapsingToolBarVerticalOffset=-1000;
    private int CollapsingToolBarTotalRange=1000;

    private List<CourseQueryCourse> mCourseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_query);

        toolbar = (Toolbar)findViewById(R.id.CourseQueryToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.CourseQueryCollapsingToolbar);
        appBarLayout = (AppBarLayout)findViewById(R.id.CourseQueryAppBarLayout);
        appBarLayout.setExpanded(false);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CollapsingToolBarVerticalOffset=verticalOffset;
                CollapsingToolBarTotalRange=appBarLayout.getTotalScrollRange();
                if(FilterIcon!=null)
                {
                    if(CollapsingToolBarVerticalOffset<=-CollapsingToolBarTotalRange/4)
                        FilterIcon.setVisible(true);
                    else FilterIcon.setVisible(false);
                }
            }
        });

        CourseQueryCourse course1 = new CourseQueryCourse();
        course1.setCourseName("课程1");
        course1.setCourseNum("00000001");
        course1.setTeacherName("教师1");
        course1.setTeacherNum("1001");
        course1.setCourseCredit(1);
        course1.setCourseTime("一1-2");
        course1.setCourseRoom("A101");
        course1.setCourseCapacity(30);
        course1.setCourseChosen(10);
        course1.setCourseCampus(CourseStatus.DISTRICT_BEN_BU);
        course1.setCourseRestriction(CourseStatus.RESTRICT_NULL);

        CourseQueryCourse course2 = new CourseQueryCourse();
        course2.setCourseName("课程2");
        course2.setCourseNum("00000002");
        course2.setTeacherName("教师2");
        course2.setTeacherNum("1002");
        course2.setCourseCredit(2);
        course2.setCourseTime("一3-4");
        course2.setCourseRoom("B102");
        course2.setCourseCapacity(100);
        course2.setCourseChosen(90);
        course2.setCourseCampus(CourseStatus.DISTRICT_ALL);
        course2.setCourseRestriction(CourseStatus.RESTRICT_STUDENT_LIMIT);

        mCourseList.add(course1);
        mCourseList.add(course2);

        RecyclerView listView = (RecyclerView)findViewById(R.id.CourseQueryRecyclerView);
        CourseQueryRecyclerViewAdapter adapter = new CourseQueryRecyclerViewAdapter(mCourseList);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
        listView.addItemDecoration(new RecyclerViewDivider(this,R.drawable.recyclerview_divider));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_query_toolbar_menu,menu);
        FilterIcon=menu.findItem(R.id.CourseQueryToolbar_Filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.CourseQueryToolbar_Filter:
                appBarLayout.setEnabled(true);
                appBarLayout.setExpanded(true);
                break;
            case R.id.CourseQueryToolbar_Search:
                break;
            case android.R.id.home:
                finish();
            default:break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
                //检查AppBarLayout垂直位置，决定收起还是放下
                if(CollapsingToolBarVerticalOffset<=-CollapsingToolBarTotalRange/4)
                    appBarLayout.setExpanded(false);//小于3/4,收起
                else appBarLayout.setExpanded(true);//大于3/4展开
        }
        return true;
    }
}
