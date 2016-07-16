package com.m520it.blacknews.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.m520it.blacknews.onclicklistener.RingTextOnclickListener;

/**
 * @author michael
 * @time 2016/6/25  10:15
 * @desc ${TODD}
 */
public class RingTextView extends View {
    TextPaint mPaint;
    int diameter;
    int padding = 4;
    Paint circlePaint;
    int center;
    String content = "跳过";
    RectF rectF;
    Paint ringPaint;
    int sweap;
    RingTextOnclickListener listener;

    public void setOnclickListener(RingTextOnclickListener listener) {
        this.listener = listener;
    }

    public RingTextView(Context context) {
        super(context);
    }

    public RingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new TextPaint();
        //初始化一个paint
        mPaint.setTextSize(20);
        mPaint.setColor(Color.WHITE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //计算出文字的宽度
        float text_width = mPaint.measureText(content);
        //直径=3*padding+文字宽度+3*padding
        diameter = (int) (text_width + padding * 6);
        center = diameter / 2;

        circlePaint = new Paint();
        circlePaint.setStrokeWidth(3);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.GRAY);

        ringPaint = new Paint();
        ringPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setColor(Color.BLUE);
        ringPaint.setStrokeWidth(3);
        ringPaint.setStyle(Paint.Style.STROKE);

        rectF = new RectF(0, 0, diameter, diameter);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //控件最外层是一个圆，圆的宽和高都是一样的

        setMeasuredDimension(diameter, diameter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(center, center, center, circlePaint);
        int y = diameter / 2 + 10 + (int) (mPaint.ascent() + mPaint.descent()) / 2;
        canvas.drawText(content, padding * 3, y, mPaint);
        //将画笔逆时针旋转90度
        canvas.save();
        //canvas.rotate(-progress, radius, radius);
        canvas.drawArc(rectF, 0, sweap, false, ringPaint);
        canvas.restore();
    }

    public void setProgress(int now, int all) {
        int space = 360 / all;
        sweap = now * space;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.3f);
                break;
            case MotionEvent.ACTION_UP:
                if(listener!=null) {
                    listener.click();
                }
                setAlpha(1.0f);
                break;
        }
        return true;
    }
}
