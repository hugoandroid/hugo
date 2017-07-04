package com.hwl.universitypie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Hugo on 2017/6/14.
 */

public class AnimView extends FrameLayout {

    private int[] drawables;
    private Random random;
    private FrameLayout.LayoutParams lp;
    private int mWidth;
    private int mHeight;
    private Interpolator[] interpolators;
    private PointF mPointInit;

    public AnimView(@NonNull Context context) {
        this(context,null);
    }
    public AnimView(@NonNull Context context, AttributeSet set) {
        super(context,set);
        init();
        setBackgroundColor(Color.WHITE);
    }

    private void init() {
        drawables = new int[]{R.drawable.anim_good_one, R.drawable.anim_good_two, R.drawable.anim_good_three,
                R.drawable.anim_good_four, R.drawable.anim_good_five, R.drawable.anim_good_six, R.drawable.anim_good_seven, R.drawable.anim_good_eight};
        random = new Random(drawables.length);
        interpolators = new Interpolator[]{new LinearInterpolator(),new AccelerateInterpolator(),new DecelerateInterpolator(),new AccelerateDecelerateInterpolator()};
    }
    public void addAnim() {
        if(lp == null){
            mWidth = getWidth();
            mHeight = getHeight();
            lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            mPointInit = new PointF((mWidth - 126) / 2, (mHeight - 126) / 2);
        }
        ImageView imageView = new ImageView(getContext());
        //随机选一个
        imageView.setImageResource(drawables[random.nextInt(8)]);
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(imageView);
        Animator set = getAnimator(imageView);
        set.addListener(new AnimEndListener(imageView));
        set.start();
    }
    private Animator getAnimator(View target) {
        AnimatorSet set = getEnterAnimtor(target);
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set, bezierValueAnimator);
        finalSet.setInterpolator(interpolators[random.nextInt(3)]);
        finalSet.setTarget(target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimtor(final View target) {

        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }

    private ValueAnimator getBezierValueAnimator(View target) {
        //初始化一个贝塞尔计算器- - 传入
        Pair<PointF, PointF> pointF = getPointF();
        BezierEvaluator evaluator = new BezierEvaluator(pointF.first, pointF.second);
        //这里最好画个图 理解一下 传入了起点 和 终点
        // ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF((mWidth - dWidth) / 2, mHeight - dHeight), new PointF(random.nextInt(getWidth()), 0));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, mPointInit, new PointF(random.nextInt(50)-150, random.nextInt(100)-400));
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }

    /**
     * 获取中间的两个 点
     */
    private Pair<PointF,PointF> getPointF() {

        PointF pointF = new PointF();
        pointF.x =random.nextInt(100) - 50;//减去100 是为了控制 x轴活动范围,看效果 随意~~
        //再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些  也可以用其他方法
        pointF.y = -random.nextInt(400);
        PointF pointF1 = new PointF(pointF.x*random.nextFloat(),pointF.y*2);
        return new Pair<>(pointF,pointF1);
    }

    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        public BezierListenr(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // 这里顺便做一个alpha动画
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }


    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
        }
    }
}
