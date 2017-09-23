package com.hzastudio.easyshu.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.support.tool.CourseProcessor;

import java.util.List;

/**
 * 课表RecyclerView适配器
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class CourseTableRecyclerViewAdapter extends RecyclerView.Adapter<CourseTableRecyclerViewAdapter.ViewHolder>{

    private List<TableCourse> mCourseList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView CourseStartTime;
        TextView CourseEndTime;
        TextView CourseName;
        TextView CourseTeacher;
        TextView CoursePlace;
        TextView CourseTimeNum;
        LinearLayout layout1;
        LinearLayout layout2;
        ImageView Teacher;
        ImageView ClassRoom;
        ImageView Indicator;

        ViewHolder(View view) {
            super(view);
            Indicator=(ImageView)view.findViewById(R.id.CurrentCourseIndicator);
            CourseTimeNum=(TextView)view.findViewById(R.id.CourseTimeNum);
            CourseStartTime=(TextView)view.findViewById(R.id.CourseStartTime);
            CourseEndTime=(TextView)view.findViewById(R.id.CourseEndTime);
            cardView=(CardView)view.findViewById(R.id.CourseInfoCardView);
            CourseName =(TextView) view.findViewById(R.id.CourseName);
            CourseTeacher=(TextView)view.findViewById(R.id.CourseTeacher);
            CoursePlace=(TextView)view.findViewById(R.id.CoursePlace);
            layout1=(LinearLayout)view.findViewById(R.id.Frame1);
            layout2=(LinearLayout)view.findViewById(R.id.Frame2);
            Teacher=(ImageView)view.findViewById(R.id.Teacher);
            ClassRoom=(ImageView)view.findViewById(R.id.Classroom);
        }
    }

    public CourseTableRecyclerViewAdapter(List<TableCourse> mCourseList) {
        this.mCourseList = mCourseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_course_table_child_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:单击事件
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.CourseTimeNum.setText("1");
                holder.CourseStartTime.setText("8:00");
                holder.CourseEndTime.setText("8:45");
                break;
            case 1:
                holder.CourseTimeNum.setText("2");
                holder.CourseStartTime.setText("8:55");
                holder.CourseEndTime.setText("9:40");
                break;
            case 2:
                holder.CourseTimeNum.setText("3");
                holder.CourseStartTime.setText("10:00");
                holder.CourseEndTime.setText("10:45");
                break;
            case 3:
                holder.CourseTimeNum.setText("4");
                holder.CourseStartTime.setText("10:55");
                holder.CourseEndTime.setText("11:40");
                break;
            case 4:
                holder.CourseTimeNum.setText("5");
                holder.CourseStartTime.setText("12:10");
                holder.CourseEndTime.setText("12:55");
                break;
            case 5:
                holder.CourseTimeNum.setText("6");
                holder.CourseStartTime.setText("13:05");
                holder.CourseEndTime.setText("13:50");
                break;
            case 6:
                holder.CourseTimeNum.setText("7");
                holder.CourseStartTime.setText("14:10");
                holder.CourseEndTime.setText("14:55");
                break;
            case 7:
                holder.CourseTimeNum.setText("8");
                holder.CourseStartTime.setText("15:05");
                holder.CourseEndTime.setText("15:50");
                break;
            case 8:
                holder.CourseTimeNum.setText("9");
                holder.CourseStartTime.setText("16:00");
                holder.CourseEndTime.setText("16:45");
                break;
            case 9:
                holder.CourseTimeNum.setText("10");
                holder.CourseStartTime.setText("16:55");
                holder.CourseEndTime.setText("17:40");
                break;
            case 10:
                holder.CourseTimeNum.setText("11");
                holder.CourseStartTime.setText("18:00");
                holder.CourseEndTime.setText("18:45");
                break;
            case 11:
                holder.CourseTimeNum.setText("12");
                holder.CourseStartTime.setText("18:55");
                holder.CourseEndTime.setText("19:40");
                break;
            case 12:
                holder.CourseTimeNum.setText("13");
                holder.CourseStartTime.setText("19:50");
                holder.CourseEndTime.setText("20:35");
                break;
            default:
                break;
        }
        TableCourse course = mCourseList.get(position);
        if (course != null && CourseProcessor.getTableCourseWeek(course)
                .contains(CourseProcessor.getCurrentWeek())) {
            holder.CourseName.setText(course.getCourseName());
            holder.CourseTeacher.setText(course.getTeacherName());
            holder.CoursePlace.setText(course.getCoursePlace());
            holder.Teacher.setVisibility(View.VISIBLE);
            holder.ClassRoom.setVisibility(View.VISIBLE);
            if (course.getCourseIsCurrent()) holder.Indicator.setVisibility(View.VISIBLE);
            else holder.Indicator.setVisibility(View.INVISIBLE);
        } else {
            holder.CourseName.setText(null);
            holder.CourseTeacher.setText(null);
            holder.CoursePlace.setText(null);
            holder.Teacher.setVisibility(View.INVISIBLE);
            holder.ClassRoom.setVisibility(View.INVISIBLE);
            holder.Indicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }
}
