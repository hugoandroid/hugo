package test.hugo.ui;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import test.hugo.R;

public class BasicEndActivity extends AppCompatActivity {

    private ImageView ivToolhead;
    private TextView tvText;
    private ImageView iv_root;
    private ImageView iv_root2;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_end);
        ivToolhead = (ImageView) findViewById(R.id.imageView);
        tvText = (TextView) findViewById(R.id.editText);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_root = (ImageView) findViewById(R.id.iv_root);
        iv_root2 = (ImageView) findViewById(R.id.iv_root2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateRevealShow();
            }
        },500);
    }

    private static final String OPTION_IMAGE = "image";

    public static void StartOptionsActivity(AppCompatActivity activity, View transitionView) {
        Intent intent = new Intent(activity, BasicEndActivity.class);
        // 这里指定了共享的视图元素
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, OPTION_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        int cx = (iv_root2.getLeft() + iv_root2.getRight()) / 2;
        int cy = (iv_root2.getTop() + iv_root2.getBottom()) / 2;
        int finalRadius = Math.max(iv_root2.getWidth(), iv_root2.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(iv_root2, cx, cy, finalRadius / 3, finalRadius);
        anim.setDuration(100);
        anim.start();
        //开始前设置标题的背景
        iv_root2.setImageResource(R.color.colorPrimary);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                //设置一个显示的动画
                iv_root.animate().alpha(1).setStartDelay(100).start();
                tvText.animate().alpha(1).setStartDelay(100).start();
                tv_title.animate().alpha(1).setStartDelay(100).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
