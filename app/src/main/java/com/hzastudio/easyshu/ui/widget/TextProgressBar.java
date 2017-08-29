package com.hzastudio.easyshu.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TextProgressBar extends ProgressBar {

    public static final int PROGRESS_NO_TEXT=0;
    public static final int PROGRESS_TEXT_PERCENT=1;
    public static final int PROGRESS_TEXT_NUM=2;

    private int TextStyle=PROGRESS_NO_TEXT;
    private String TextToDraw="";
    private int TextSize=20;
    private int TextColor=Color.BLACK;

    private Paint mPaint;
    private Context context;
    private Rect rect = new Rect();

    public void setTextStyle(int textStyle) {
        TextStyle = textStyle;
        initPreProgressBar();
    }

    public void setTextSize(int textSize) {
        TextSize = textSize;
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        this.mPaint.setTextSize(TextSize * fontScale + 0.5f);
        initPreProgressBar();
    }

    public void setTextColor(int color) {
        TextColor = color;
        initPreProgressBar();
    }

    public TextProgressBar(Context context) {
        super(context);
        this.context=context;
        initPreProgressBar();
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context=context;
        initPreProgressBar();
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initPreProgressBar();
    }

    private void initPreProgressBar() {
        switch (TextStyle){
            case PROGRESS_NO_TEXT:break;
            case PROGRESS_TEXT_PERCENT:TextToDraw="0%";break;
            case PROGRESS_TEXT_NUM:TextToDraw="0/"+getMax();break;
            default:break;
        }
        this.mPaint = new Paint();
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        this.mPaint.setTextSize(TextSize * fontScale + 0.5f);
        this.mPaint.setAntiAlias(true);// 设置抗锯齿;
        this.mPaint.setColor(TextColor);
        setIndeterminate(false);
        setProgressDrawable(getProgressDrawable());
        setProgressText(0);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        setProgressText(progress);
    }

    private void setProgressText(int progress) {
        switch (TextStyle){
            case PROGRESS_NO_TEXT:break;
            case PROGRESS_TEXT_PERCENT:
                int i = (int) ((progress * 1.0f / this.getMax()) * 100);
                this.TextToDraw = String.valueOf(i) + "%";
                break;
            case PROGRESS_TEXT_NUM:
                this.TextToDraw = progress + "/" + this.getMax();
                break;
            default:break;
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.getTextBounds(this.TextToDraw,0, this.TextToDraw.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();// 让显示的字体处于中心位置;
        int y = (getHeight() / 2) - rect.centerY();// 让显示的字体处于中心位置;
        canvas.drawText(this.TextToDraw, x, y, this.mPaint);
    }
}
