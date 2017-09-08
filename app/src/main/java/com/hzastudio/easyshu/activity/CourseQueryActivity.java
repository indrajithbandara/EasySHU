package com.hzastudio.easyshu.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseQueryOptionsRecyclerViewAdapter;
import com.hzastudio.easyshu.adapter.CourseQueryRecyclerViewAdapter;
import com.hzastudio.easyshu.module.XKSystemHandler;
import com.hzastudio.easyshu.support.data_bean.CourseOption;
import com.hzastudio.easyshu.support.data_bean.CourseOptionData;
import com.hzastudio.easyshu.support.data_bean.CourseQueryCourse;
import com.hzastudio.easyshu.support.program_const.Option;
import com.hzastudio.easyshu.support.universal.ActivityCollector;
import com.hzastudio.easyshu.support.universal.BaseActivity;
import com.hzastudio.easyshu.support.universal.MainApplication;
import com.hzastudio.easyshu.ui.widget.RecyclerViewDivider;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CourseQueryActivity extends BaseActivity {

    @BindView(R.id.CourseQueryToolbar)
    Toolbar toolbar;
    @BindView(R.id.CourseQueryAppBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.CourseQueryRecyclerView)
    XRecyclerView listView;
    @BindView(R.id.CourseQueryOptionsRecyclerView)
    RecyclerView optionView;
    @BindView(R.id.CourseQueryToolbar_SearchText)
    TextView searchText;
    @BindView(R.id.CourseQueryRefresh)
    SwipeRefreshLayout refresh;

    MenuItem FilterIcon, FoldIcon;

    CourseQueryOptionsRecyclerViewAdapter optionAdapter;
    CourseQueryRecyclerViewAdapter ListAdapter;

    private int CollapsingToolBarVerticalOffset = -10000;
    private int CollapsingToolBarTotalRange = 10000;

    private List<CourseQueryCourse> mCourseList = new ArrayList<>();
    private CourseOptionData data;

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
                CollapsingToolBarVerticalOffset = verticalOffset;
                CollapsingToolBarTotalRange = appBarLayout.getTotalScrollRange();
                if (optionView != null) {
                    optionView.setAlpha(1 + ((float) CollapsingToolBarVerticalOffset) / CollapsingToolBarTotalRange);
                }
                if (FilterIcon != null) {
                    if (CollapsingToolBarVerticalOffset <= -CollapsingToolBarTotalRange / 4) {
                        FilterIcon.setVisible(true);
                        FoldIcon.setVisible(false);
                    } else {
                        FilterIcon.setVisible(false);
                        FoldIcon.setVisible(true);
                    }
                }
            }
        });
        /*appBarLayout标题栏布局*************************END**********/

        /*SwipeRefreshLayout刷新布局********************START********/
        refresh.setEnabled(false);
        /*SwipeRefreshLayout刷新布局*********************END*********/

        /*RecyclerView:课程*************************START**********/
        //初始化适配器
        listView.setPullRefreshEnabled(false);
        listView.setLoadingMoreEnabled(false);
        listView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        ListAdapter = new CourseQueryRecyclerViewAdapter(mCourseList);
        listView.setAdapter(ListAdapter);
        listView.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
        listView.addItemDecoration(new RecyclerViewDivider(this, R.drawable.recyclerview_divider));
        listView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //Disabled
            }

            @Override
            public void onLoadMore() {
                QueryCourses();
            }
        });
        /*RecyclerView:课程*************************END**********/

        /*RecyclerView:选项*************************START**********/
        optionAdapter = new CourseQueryOptionsRecyclerViewAdapter(Option.getCourseOption());
        optionView.setAdapter(optionAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(MainApplication.getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        optionView.setLayoutManager(manager);
        /*RecyclerView:选项*************************END**********/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_query_toolbar_menu, menu);
        FilterIcon = menu.findItem(R.id.CourseQueryToolbar_Filter);
        FoldIcon = menu.findItem(R.id.CourseQueryToolbar_Fold);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.CourseQueryToolbar_Filter:
                appBarLayout.setExpanded(true);
                break;
            case R.id.CourseQueryToolbar_Fold:
                appBarLayout.setExpanded(false);
                break;
            case R.id.CourseQueryToolbar_Search:
                listView.setNoMore(false);
                refresh.setEnabled(true);
                refresh.setRefreshing(true);
                ListAdapter.RemoveAllItems();
                CompileQueryOptions();
                QueryCourses();
                break;
            case android.R.id.home:
                ActivityCollector.RemoveActivity(this);
            default:
                break;
        }
        return true;
    }

    private void CompileQueryOptions() {
        //获取选项列表和参数列表和课程名/课程号
        List<String> optionData = optionAdapter.getmOptionDataList();
        List<CourseOption> options = Option.getCourseOption();
        CourseOptionData.Builder builder = new CourseOptionData.Builder();
        String NameORNum = searchText.getText().toString();
        if (isInteger(searchText.getText().toString())) builder.setCourseNum(NameORNum);
        else builder.setCourseName(NameORNum);
        for (int i = 0; i < optionData.size(); i++) {
            switch (options.get(i).getTag()) {
                case Option.Option_CourseTeacherNum:
                    builder.setTeacherNum(optionData.get(i));
                    break;
                case Option.Option_CourseTeacher:
                    builder.setTeacherName(optionData.get(i));
                    break;
                case Option.Option_CourseTime:
                    builder.setCourseTime(optionData.get(i));
                    break;
                case Option.Option_IsCourseFull:
                    builder.setCourseNotFull(optionData.get(i));
                    break;
                case Option.Option_CourseCredit:
                    builder.setCourseCredit(optionData.get(i));
                    break;
                case Option.Option_CourseCampus:
                    builder.setCourseCampus(optionData.get(i));
                    break;
                case Option.Option_CourseChooseNum:
                    builder.setCourseEnroll(optionData.get(i));
                    break;
                case Option.Option_CourseChooseNumField:
                    if(!optionData.get(i).equals("")) {
                        String[] tmp = optionData.get(i).split("-");
                        builder.setCourseCapacity(tmp[0], tmp[1]);
                    }
                    break;
                default:
                    break;
            }
        }
        data = builder.build();

    }

    public void QueryCourses() {
        XKSystemHandler.QueryCourse(data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CourseQueryCourse>>() {
                    @Override
                    public void accept(@NonNull List<CourseQueryCourse> courseQueryCourses) throws Exception {
                        listView.setLoadingMoreEnabled(true);
                        if(refresh.isRefreshing()){
                            refresh.setRefreshing(false);
                            refresh.setEnabled(false);
                        }
                        ListAdapter.AddItems(courseQueryCourses);
                        listView.loadMoreComplete();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listView.setNoMore(true);
                        listView.setLoadingMoreEnabled(false);
                        Snackbar.make(listView,"没有更多了...",Snackbar.LENGTH_SHORT).show();
                        Log.e("sss","warning!!!");
                    }
                });
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
