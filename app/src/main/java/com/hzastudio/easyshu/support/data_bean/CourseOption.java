package com.hzastudio.easyshu.support.data_bean;

import android.view.View;

import com.hzastudio.easyshu.support.program_const.Option;

public class CourseOption {

    /**
     * 课程筛选选项分类有如下几种：
     * 1.布尔式：选课是否已满
     * 2.值式：学分，选课人数，校区
     * 3.区间式：容量空余
     * 4.字符串式：教师名，教师号
     */

    //选项的说明文字
    private String OptionDescribe = "";

    //按钮的初始化文字
    private String ButtonInitialText = "";

    //对应的单击事件响应回调
    private View.OnClickListener onClickListener;

    public CourseOption(String optionDescribe, String buttonInitialText, View.OnClickListener onClickListener) {
        OptionDescribe = optionDescribe;
        ButtonInitialText = buttonInitialText;
        this.onClickListener = onClickListener;
    }

    public String getOptionDescribe() {
        return OptionDescribe;
    }

    public void setOptionDescribe(String optionDescribe) {
        OptionDescribe = optionDescribe;
    }

    public String getButtonInitialText() {
        return ButtonInitialText;
    }

    public void setButtonInitialText(String buttonInitialText) {
        ButtonInitialText = buttonInitialText;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
