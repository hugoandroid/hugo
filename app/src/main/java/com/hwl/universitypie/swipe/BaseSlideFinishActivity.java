package com.hwl.universitypie.swipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwl.universitypie.R;

public abstract class BaseSlideFinishActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlideToFinishLayout layout = new SlideToFinishLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        LayoutInflater.from(this).inflate(initView(),layout,true);
        View.inflate(this,initView(),layout);
        setContentView(layout);
    }


    @Override
    public void onClick(View v) {
        performViewClick(v);
    }

    public abstract int initView();

    protected abstract void performViewClick(View v);

    public void finishSelf() {
        this.finish();
        this.overridePendingTransition(0, 0);//取消Activity的动画。
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
    }
}
