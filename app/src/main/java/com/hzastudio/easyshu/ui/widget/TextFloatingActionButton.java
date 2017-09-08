package com.hzastudio.easyshu.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;


public class TextFloatingActionButton extends FloatingActionButton {

    public interface OnTextToDrawChangedListener{
        String onFormat(String newText);
    }

    public interface OnFormatListener{
        void onFormatCompleted(String FormattedText);
    }

    //提供默认实现
    OnTextToDrawChangedListener formatter=new OnTextToDrawChangedListener() {
        @Override
        public String onFormat(String newText) {
            return newText;
        }
    };
    OnFormatListener listener=new OnFormatListener() {
        @Override
        public void onFormatCompleted(String FormattedText) {

        }
    };

    private Context mContext;
    private String TextToDraw="";
    private Paint mPaint;
    private int TextSize=12;
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
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*自适应外文姓名换行*/
        this.mPaint.getTextBounds(TextToDraw, 0, TextToDraw.length(), rect);
        String[] ToDraw = TextToDraw.split(" ");
        float startY;
        if (ToDraw.length % 2 != 0) {
            startY = (getHeight() / 2) - rect.centerY() - ((rect.bottom - rect.top) + 2) * (ToDraw.length / 2);
        } else {
            startY = (getHeight() / 2 + 1) - ((rect.bottom - rect.top) + 2) * (ToDraw.length / 2 - 1);
        }

        int cnt = 0;
        for (String DrawElement : ToDraw) {
            this.mPaint.getTextBounds(DrawElement, 0, DrawElement.length(), rect);
            float x = (getWidth() / 2) - rect.centerX();
            float y = startY + cnt * (2 + (rect.bottom - rect.top));
            canvas.drawText(DrawElement, x, y, this.mPaint);
            cnt++;
        }

    }

    public String getTextToDraw() {
        return TextToDraw;
    }

    public synchronized void setTextToDraw(String textToDraw) {
        TextToDraw = textToDraw;
        //格式化并输出数据（两个接口使数据格式化程序可以静态导入）
        listener.onFormatCompleted(formatter.onFormat(TextToDraw));
    }

    public void setTextSize(int textSize) {
        TextSize = textSize;
        initTextFAB();
    }

    public void setTextColor(int textColor) {
        TextColor = textColor;
        initTextFAB();
    }

    public void setOnTextToDrawListener(OnTextToDrawChangedListener formatter)
    {
        this.formatter=formatter;
    }

    public void setOnFormatListener(OnFormatListener listener) {
        this.listener = listener;
    }

}
