package com.hwl.universitypie;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 课程播放需要的顶部bar
 */

public class VideoTopBar2 extends LinearLayout {

    private TextView titleView;

    public VideoTopBar2(Context context) {
        this(context,null);

    }

    public VideoTopBar2(Context context, AttributeSet o) {
        super(context,o);
        inflate(context,R.layout.item,this);
    }
}
