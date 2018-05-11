package com.wj.dawsonwanandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wj on 2018/5/11.
 */
public class DownLoadProgress extends View {

    private int mCurrentProgress;
    private Paint leftPaint;
    private Paint rightPaint;
    private int width;
    private int height;
    private Context mContext;

    public DownLoadProgress(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public DownLoadProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public DownLoadProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        leftPaint = new Paint();
        rightPaint = new Paint();

        setPaintProperty(leftPaint);
        setPaintProperty(rightPaint);
    }

    private void setPaintProperty(Paint paint) {
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = measureSize(widthMeasureSpec);
        height = measureSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

    }

    private int measureSize(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 0;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float pointPosition = width / 2;//坐标原点

        //当前进度占进度条长度的百分比就是左右移动的总偏移量，再除以2就是左边的偏移量
        int offset = mCurrentProgress * width / 100 / 2;

        //左边线往左画
        float leftOffset = pointPosition - offset;
        //右边线往右画
        float rightOffset = pointPosition + offset;

        //从坐标原点往左画
        canvas.drawLine(pointPosition, 0, leftOffset, 0, leftPaint);
        //从坐标原点往右画
        canvas.drawLine(pointPosition, 0, rightOffset, 0, rightPaint);
    }

    //设置进度值取值范围是[0,100]
    public void setProgress(int progress) {
        mCurrentProgress = progress;
        invalidate();
    }
}
