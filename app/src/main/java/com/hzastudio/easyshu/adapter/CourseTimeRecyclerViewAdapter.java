package com.hzastudio.easyshu.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.data_bean.CourseOption;

import java.util.ArrayList;
import java.util.List;

public class CourseTimeRecyclerViewAdapter
        extends RecyclerView.Adapter<CourseTimeRecyclerViewAdapter.ViewHolder> {

    private List<String> mTimeList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{

        /*View元素*/
        ImageView DeleteImg;
        TextView CourseTimeText;

        ViewHolder(View view) {
            super(view);
            CourseTimeText = view.findViewById(R.id.CourseTimeText);
            DeleteImg = view.findViewById(R.id.CourseTimeDelete);
        }
    }

    public CourseTimeRecyclerViewAdapter(List<String> timeList) {
        mTimeList=new ArrayList<>();
        mTimeList.addAll(timeList);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_course_time_child_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String CourseTime = mTimeList.get(position);
        holder.CourseTimeText.setText(CourseTime);
        holder.DeleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> tmp = new ArrayList<>();
                tmp.addAll(mTimeList);
                tmp.remove(holder.getAdapterPosition());
                mTimeList=tmp;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTimeList.size();
    }

    public void addItem(String item)
    {
        mTimeList.add(item);
        List<String> tmp = new ArrayList<>();
        tmp.addAll(mTimeList);
        mTimeList=tmp;
        notifyDataSetChanged();
    }

    public List<String> getList()
    {
        return mTimeList;
    }

}
