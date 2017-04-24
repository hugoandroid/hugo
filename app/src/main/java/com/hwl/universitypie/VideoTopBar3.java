package com.hwl.universitypie;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 课程播放需要的顶部bar
 */

public class VideoTopBar3 extends LinearLayout {

    public VideoTopBar3(Context context) {
        this(context,null);

    }

    public VideoTopBar3(Context context, AttributeSet o) {
        super(context,o);
        setLayoutParams(new ViewGroup.LayoutParams(-1,getStatusBarHeight()));
        inflate(context,R.layout.item1,this);
    }
    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            return 0;
        }
    }
}
