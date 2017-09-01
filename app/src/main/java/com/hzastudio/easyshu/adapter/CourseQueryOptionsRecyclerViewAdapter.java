package com.hzastudio.easyshu.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.data_bean.CourseOption;
import com.hzastudio.easyshu.support.program_const.Option;
import com.hzastudio.easyshu.ui.widget.TextFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class CourseQueryOptionsRecyclerViewAdapter
        extends RecyclerView.Adapter<CourseQueryOptionsRecyclerViewAdapter.ViewHolder> {

    private List<CourseOption> mOptionList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{

        /*View元素*/
        TextFloatingActionButton QueryOptionButton;
        TextView QueryOptionText;

        ViewHolder(View view) {
            super(view);
            QueryOptionButton = (TextFloatingActionButton) view.findViewById(R.id.QueryOptionButton);
            QueryOptionText = (TextView) view.findViewById(R.id.QueryOptionText);
        }
    }

    public CourseQueryOptionsRecyclerViewAdapter(List<CourseOption> optionList) {
        this.mOptionList = optionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_query_option_child_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseOption option = mOptionList.get(position);

        /*初始化配置*/
        holder.QueryOptionText.setText(option.getOptionDescribe());
        holder.QueryOptionButton.setTextToDraw(option.getButtonInitialText());
        holder.QueryOptionButton.
                setOnClickListener(mOptionList.get(position).getOnClickListener());
    }

    @Override
    public int getItemCount() {
        return mOptionList.size();
    }
}
