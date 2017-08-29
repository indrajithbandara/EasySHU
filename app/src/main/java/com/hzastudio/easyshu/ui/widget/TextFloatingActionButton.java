package com.hzastudio.easyshu.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;


public class TextFloatingActionButton extends FloatingActionButton {

    private Context mContext;
    private String TextToDraw="";
    private Paint mPaint;
    private int TextSize=16;
    private int TextColor= Color.BLACK;
    private Rect rect = new Rect();

    public TextFloatingActionButton(Context context) {
        this(context, null);
    }

    public TextFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initTextFAB();
    }

    private void initTextFAB() {
        this.mPaint = new Paint();
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        this.mPaint.setTextSize(TextSize * fontScale + 0.5f);
        this.mPaint.setAntiAlias(true);// 设置抗锯齿;
        this.mPaint.setColor(TextColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.getTextBounds(this.TextToDraw,0, this.TextToDraw.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();// 让显示的字体处于中心位置;
        int y = (getHeight() / 2) - rect.centerY();// 让显示的字体处于中心位置;
        canvas.drawText(this.TextToDraw, x, y, this.mPaint);
    }

    public String getTextToDraw() {
        return TextToDraw;
    }

    public void setTextToDraw(String textToDraw) {
        TextToDraw = textToDraw;
    }

    public void setTextSize(int textSize) {
        TextSize = textSize;
        initTextFAB();
    }

    public void setTextColor(int textColor) {
        TextColor = textColor;
        initTextFAB();
    }

}
