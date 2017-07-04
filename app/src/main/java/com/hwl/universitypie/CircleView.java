package com.hwl.universitypie;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class CircleView extends View {

    private Paint mPaint;
    private Path mPath;
    private Paint mPaint1;
    private Paint mPaint2;
    private Rect mRect;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeWidth(2);
        mPaint1.setColor(Color.DKGRAY);
        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(2);
        mPaint2.setColor(Color.BLACK);

        mPath = new Path();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int width = getWidth();
        int height = getHeight();
        mRect = new Rect(0, 0, width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && mRect != null && mRect.right == 0) {
            mRect = new Rect(0, 0, w, h);
        }
    }

    //  mPath.rQuadTo(-50,550,100,400);
//        mPath.rQuadTo(100,400,250,250);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRect, mPaint);
        mPath.reset();
        mPath.moveTo(100, 100);
        canvas.drawCircle(300, 100, 20, mPaint2);
        mPath.quadTo(300, 100, 400, 200);

//        mPath.moveTo(400,200);
        canvas.drawCircle(400, 400, 20, mPaint2);
        mPath.quadTo(400, 400, 100, 700);
//        mPath.moveTo(100,700);
//        canvas.drawCircle(-200,550,10, mPaint2);
        canvas.drawCircle(250, 250, 10, mPaint2);
        mPath.rQuadTo(-150, -150, 0, -300);
        mPath.rQuadTo(150, -150, 0, -300);
//        mPath.cubicTo(-50,550,250,250,100,100);

        canvas.drawLine(100, 100, 400, 200, mPaint1);
        canvas.drawLine(400, 200, 100, 700, mPaint1);
        canvas.drawLine(100, 100, 100, 700, mPaint1);
        canvas.drawCircle(100, 100, 20, mPaint2);
        canvas.drawCircle(100, 700, 20, mPaint2);

        canvas.drawCircle(400, 200, 20, mPaint2);
//        canvas.drawCircle(500,300,20,mPaint);
//        mPath.cubicTo(200,200,400,200,500,300);
        canvas.drawPath(mPath, mPaint);
//        canvas.drawColor(Color.DKGRAY);
        mPaint.setTextSize(25f);
        canvas.drawText("三阶bers 曲线", 300, 600, mPaint);
    }
}
