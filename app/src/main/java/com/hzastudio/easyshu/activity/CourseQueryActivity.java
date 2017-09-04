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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseQueryOptionsRecyclerViewAdapter;
import com.hzastudio.easyshu.adapter.CourseQueryRecyclerViewAdapter;
import com.hzastudio.easyshu.adapter.CourseTableRecyclerViewAdapter;
import com.hzastudio.easyshu.support.data_bean.CourseQueryCourse;
import com.hzastudio.easyshu.support.program_const.CourseStatus;
import com.hzastudio.easyshu.support.program_const.Option;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.ui.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class CourseQueryActivity extends BaseActivity {

    @BindView(R.id.CourseQueryToolbar) Toolbar toolbar;
    @BindView(R.id.CourseQueryAppBarLayout) AppBarLayout appBarLayout;
    @BindView(R.id.CourseQueryRecyclerView) RecyclerView listView;
    @BindView(R.id.CourseQueryOptionsRecyclerView) RecyclerView optionView;
    MenuItem FilterIcon,FoldIcon;

    private int CollapsingToolBarVerticalOffset=-10000;
    private int CollapsingToolBarTotalRange=10000;

    private List<CourseQueryCourse> mCourseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_query);
        ButterKnife.bind(this);//必须在当前Activity而不是基类里绑定！

        /*Toolbar标题栏控件*************************START**********/
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        /*Toolbar标题栏控件*************************END**********/

        /*appBarLayout标题栏布局*************************START**********/
        appBarLayout.setExpanded(false);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CollapsingToolBarVerticalOffset=verticalOffset;
                CollapsingToolBarTotalRange=appBarLayout.getTotalScrollRange();
                if(optionView!=null)
                {
                    optionView.setAlpha(1+((float)CollapsingToolBarVerticalOffset)/CollapsingToolBarTotalRange);
                }
                if(FilterIcon!=null)
                {
                    if(CollapsingToolBarVerticalOffset<=-CollapsingToolBarTotalRange/4)
                    {
                        FilterIcon.setVisible(true);
                        FoldIcon.setVisible(false);
                    }
                    else {
                        FilterIcon.setVisible(false);
                        FoldIcon.setVisible(true);
                    }
                }
            }
        });
        /*appBarLayout标题栏布局*************************END**********/

        /*RecyclerView:课程*************************START**********/
        CourseQueryRecyclerViewAdapter adapter = new CourseQueryRecyclerViewAdapter(mCourseList);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
        listView.addItemDecoration(new RecyclerViewDivider(this,R.drawable.recyclerview_divider));
        /*RecyclerView:课程*************************END**********/

        /*RecyclerView:选项*************************START**********/
        CourseQueryOptionsRecyclerViewAdapter optionAdapter =
                new CourseQueryOptionsRecyclerViewAdapter(Option.getCourseOption());
        optionView.setAdapter(optionAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(MainApplication.getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        optionView.setLayoutManager(manager);
        /*RecyclerView:选项*************************END**********/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_query_toolbar_menu,menu);
        FilterIcon=menu.findItem(R.id.CourseQueryToolbar_Filter);
        FoldIcon=menu.findItem(R.id.CourseQueryToolbar_Fold);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.CourseQueryToolbar_Filter:
                appBarLayout.setExpanded(true);
                break;
            case R.id.CourseQueryToolbar_Fold:
                appBarLayout.setExpanded(false);
                break;
            case R.id.CourseQueryToolbar_Search:
                break;
            case android.R.id.home:
                finish();
            default:break;
        }
        return true;
    }

}
