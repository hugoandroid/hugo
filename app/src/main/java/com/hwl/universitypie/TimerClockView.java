package com.hwl.universitypie;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TimerClockView extends LinearLayout{

    private MyHandler mHandler;
    private TextView mTv_hour01;
    private TextView mTv_hour02;
    private TextView mTv_minute01;
    private TextView mTv_minute02;
    private TextView mTv_second01;
    private TextView mTv_second02;
    private int leftTime;

    public TimerClockView(Context context) {
        this(context,null);
    }
    public TimerClockView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        mHandler = new MyHandler(this);
        inflate(context,R.layout.view_timer_clock,this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTv_hour01 = (TextView) findViewById(R.id.tv_hour01);
        mTv_hour02 = (TextView) findViewById(R.id.tv_hour02);

        mTv_minute01 = (TextView) findViewById(R.id.tv_minute01);
        mTv_minute02 = (TextView) findViewById(R.id.tv_minute02);

        mTv_second01 = (TextView) findViewById(R.id.tv_second01);
        mTv_second02 = (TextView) findViewById(R.id.tv_second02);
    }
    public void setLeftTimeAndBeginClock(int leftTime){
        this.leftTime = leftTime;
        refreshData();
    }
    private void refreshData() {
        int seconds = leftTime % 60;
        int minutes = (leftTime / 60) % 60;
        int hours = leftTime / 3600;
        if(hours>9){
            mTv_hour01.setText(hours/10);
            mTv_hour02.setText(hours%10);
        }else{
            mTv_hour01.setText("0");
            mTv_hour02.setText(String.valueOf(hours));
        }
        if(minutes>9){
            mTv_minute01.setText(String.valueOf(minutes/10));
            mTv_minute02.setText(String.valueOf(minutes%10));
        }else{
            mTv_minute01.setText("0");
            mTv_minute02.setText(String.valueOf(minutes));
        }
        if(seconds>9){
            mTv_second01.setText(String.valueOf(seconds/10));
            mTv_second02.setText(String.valueOf(seconds%10));
        }else{
            mTv_second01.setText("0");
            mTv_second02.setText(String.valueOf(seconds));
        }
        if(--leftTime>0)
            mHandler.sendEmptyMessageDelayed(1,1000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(1);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<TimerClockView> mRf;

        private MyHandler(TimerClockView view){
            mRf = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            TimerClockView view = mRf.get();
            if(view == null)return;
            view.refreshData();
        }
    }
}
