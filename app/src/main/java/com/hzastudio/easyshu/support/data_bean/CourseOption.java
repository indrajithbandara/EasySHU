package com.hzastudio.easyshu.support.data_bean;

import android.view.View;

import com.hzastudio.easyshu.support.program_const.Option;
import com.hzastudio.easyshu.ui.widget.TextFloatingActionButton;

/**
 * 课程选项bean
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class CourseOption {

    /**
     * 课程筛选选项分类有如下几种：
     * 1.布尔式：选课是否已满
     * 2.值式：学分，选课人数，校区
     * 3.区间式：容量空余
     * 4.字符串式：教师名，教师号
     */

    //选项的TAG
    private int Tag=0;

    //选项的说明文字
    private String OptionDescribe = "";

    //按钮的初始化文字
    private String ButtonInitialText = "";

    //初始化数据
    private String InitialData = "";

    //对应的单击事件响应回调
    private View.OnClickListener onClickListener;

    //对应的内容格式化回调
    private TextFloatingActionButton.OnTextToDrawChangedListener onTextToDrawChangedListener;

    public CourseOption(int tag, String optionDescribe, String buttonInitialText, String initialData, View.OnClickListener onClickListener, TextFloatingActionButton.OnTextToDrawChangedListener onTextToDrawChangedListener) {
        Tag = tag;
        OptionDescribe = optionDescribe;
        ButtonInitialText = buttonInitialText;
        InitialData=initialData;
        this.onClickListener = onClickListener;
        this.onTextToDrawChangedListener = onTextToDrawChangedListener;
    }

    public CourseOption(int tag, String optionDescribe, String buttonInitialText, String initialData, View.OnClickListener onClickListener) {
        Tag = tag;
        OptionDescribe = optionDescribe;
        ButtonInitialText = buttonInitialText;
        this.onClickListener = onClickListener;
        this.onTextToDrawChangedListener = new TextFloatingActionButton.OnTextToDrawChangedListener() {
            @Override
            public String onFormat(String newText) {
                if(newText.equals("---"))return "";
                return newText;
            }
        };
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

    public String getInitialData() {
        return InitialData;
    }

    public void setInitialData(String initialData) {
        InitialData = initialData;
    }

    public int getTag() {
        return Tag;
    }

    public void setTag(int tag) {
        Tag = tag;
    }

    public TextFloatingActionButton.OnTextToDrawChangedListener getOnTextToDrawChangedListener() {
        return onTextToDrawChangedListener;
    }

    public void setOnTextToDrawChangedListener(TextFloatingActionButton.OnTextToDrawChangedListener onTextToDrawChangedListener) {
        this.onTextToDrawChangedListener = onTextToDrawChangedListener;
    }
}
