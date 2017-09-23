package com.hzastudio.easyshu.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.universal.MainApplication;

/**
 * 带删除按钮的输入框
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class DeleteEditText extends android.support.v7.widget.AppCompatEditText {

    private Context mContext;
    private boolean clearVisible = false;
    Drawable DrawableRight,DrawableRightPressed;

    public DeleteEditText(Context context) {
        this(context, null);
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public DeleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        Initialize();
    }

    /* 初始化 */
    private void Initialize() {

        //初始化输入框背景
        //Drawable back = mContext.getResources().getDrawable(R.drawable.search_back);
        //setBackground(back);

        //初始化并隐藏删除图标
        DrawableRight = mContext.getResources().getDrawable(R.drawable.clear_normal);
        DrawableRightPressed = mContext.getResources().getDrawable(R.drawable.clear_pressed);
        ApplyDrawable(false);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0)
                {
                    clearVisible = true;
                    ApplyDrawable(false);
                }
            }
        });

    }

    private void ApplyDrawable(boolean clearPressed)
    {
        setCompoundDrawablesWithIntrinsicBounds(null, null,
                clearVisible ? (clearPressed ? DrawableRightPressed : DrawableRight) : null,
                null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(clearVisible) {
                    float x = event.getX();
                    if (x > (getWidth() - getCompoundDrawables()[2].getIntrinsicWidth() + 10) && x < getWidth()) {
                        //按下了删除按钮
                        ApplyDrawable(true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(clearVisible)
                {
                    //清除删除按钮的按下状态
                    ApplyDrawable(false);
                    float x = event.getX();
                    if (x > (getWidth() - getCompoundDrawables()[2].getIntrinsicWidth() + 10) && x < getWidth()) {
                        //刚刚按下的是删除按钮
                        setText("");
                        clearVisible = false;
                        ApplyDrawable(false);
                    }
                }
                break;
            default:break;
        }
        return true;
    }

}
