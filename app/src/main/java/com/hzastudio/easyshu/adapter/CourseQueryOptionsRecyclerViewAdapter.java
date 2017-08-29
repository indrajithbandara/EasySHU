package com.hzastudio.easyshu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.data_bean.CourseOption;
import com.hzastudio.easyshu.support.data_bean.TableCourse;
import com.hzastudio.easyshu.ui.widget.TextFloatingActionButton;

import java.util.List;

public class CourseQueryOptionsRecyclerViewAdapter
        extends RecyclerView.Adapter<CourseQueryOptionsRecyclerViewAdapter.ViewHolder> {

    private List<CourseOption> mOptionList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextFloatingActionButton QueryOptionButton;
        TextView QueryOptionText;

        ViewHolder(View view) {
            super(view);
            QueryOptionButton = (TextFloatingActionButton) view.findViewById(R.id.QueryOptionButton);
            QueryOptionText = (TextView) view.findViewById(R.id.QueryOptionText);
        }
    }

    public CourseQueryOptionsRecyclerViewAdapter(List<CourseOption> mOptionList) {
        this.mOptionList = mOptionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_query_option_child_layout,parent,false);
        final ViewHolder holder= new ViewHolder(view);
        holder.QueryOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:点击事件
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseOption option = mOptionList.get(position);
        
    }

    @Override
    public int getItemCount() {
        return mOptionList.size();
    }
}
