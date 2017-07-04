package test.hugo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * 课程播放需要的顶部bar
 */

public class VideoTopBar extends LinearLayout implements View.OnClickListener {

    private TextView titleView;
    private StringBuilder mStringBuilder;

    public VideoTopBar(Context context) {
        this(context,null);
    }

    public VideoTopBar(Context context, AttributeSet o) {
        super(context,o);
        setOrientation(HORIZONTAL);
        int dp2px = dp2px(36);
        int barHeight = getStatusBarHeight();
        setBackgroundColor(Color.argb(99,0,0,0));
        if(Build.VERSION.SDK_INT > 18){
            setLayoutParams(new LayoutParams(-1,dp2px+barHeight));
            setPadding(0,barHeight,0,0);
        } else {
            setLayoutParams(new LayoutParams(-1,dp2px));
        }

        ImageView imageView = new ImageView(context);
        LayoutParams params1 = new LayoutParams(dp2px,dp2px);
        params1.setMargins(dp2px(5),0,0,0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setLayoutParams(params1);
        addView(imageView);
        imageView.setOnClickListener(this);


        titleView = new TextView(context);
        titleView.setMaxLines(1);
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setIncludeFontPadding(false);
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams param2 = new LayoutParams(0,-1);
        param2.weight = 1;
        titleView.setTextColor(Color.WHITE);
        titleView.setTextSize(16f);
        titleView.getPaint().setFakeBoldText(true);
        titleView.setLayoutParams(param2);
        addView(titleView);

        ImageView rightImage = new ImageView(context);
        rightImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        rightImage.setImageResource(R.mipmap.ic_launcher);
        LayoutParams params3 = new LayoutParams(dp2px,dp2px);
        params3.setMargins(0,0,dp2px(5),0);
        rightImage.setLayoutParams(params3);
        addView(rightImage);
        rightImage.setOnClickListener(this);
    }

    private int dp2px(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5);
    }

    public void setTitle(CharSequence title){
        titleView.setText(title);
    }
    private ClickCallBack mCallBack;
    @Override
    public void onClick(View v) {
        if(mCallBack == null)return;
        mCallBack.onTitleClick(v.getId() == R.id.tag_first);
    }

    public void setClickCallBack(ClickCallBack callBack) {
        this.mCallBack = callBack;
    }

    public String getTitle() {
        return titleView.getText().toString();
    }

    public interface ClickCallBack{
        void onTitleClick(boolean isLeftView);
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
    public String settext(){
        mStringBuilder = new StringBuilder();
        return getFather(titleView);
    }
    private String getFather(View view) {
        ViewParent parent = view.getParent();
        if(parent instanceof ScrollView){
            mStringBuilder.append("ScrollView");
        }else{
            mStringBuilder.append(parent.toString()+"   ");
            getFather((View) parent);
        }
        return mStringBuilder.toString();
    }
}
