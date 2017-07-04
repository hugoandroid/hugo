package com.hwl.universitypie;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;


public class OverScrollView extends ScrollView {
    private View headView;
    private int mDrawableHeight;
    private int mOriginalHeight;

    public OverScrollView(Context context) {
        super(context);
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //去掉下拉到头部后的蓝色线
        setOverScrollMode(OVER_SCROLL_NEVER);
    }
    public void setHeadViewAndMaxHeight(View head,int maxHeight){
        this.headView = head;
        headView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mOriginalHeight = headView.getHeight();
            }
        });
        this.mDrawableHeight =maxHeight;
    }
    /**
     * 当滑动的超出上,下,左,右最大范围时回调
     *
     * @param deltaX         x方向的瞬时偏移量,左边到头,向右拉为负,右边到头,向左拉为正
     * @param deltaY         y方向的瞬时偏移量,顶部到头,向下拉为负,底部到头,向上拉为正
     * @param scrollX        水平方向的永久偏移量
     * @param scrollY        竖直方向的永久偏移量
     * @param scrollRangeX   水平方向滑动的范围
     * @param scrollRangeY   竖直方向滑动的范围
     * @param maxOverScrollX 水平方向最大滑动范围
     * @param maxOverScrollY 竖直方向最大滑动范围
     * @param isTouchEvent   是否是手指触摸滑动, true为手指, false为惯性
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {
        // 手指拉动并且是下拉
        if (isTouchEvent && deltaY < 0) {
            // 把拉动的瞬时变化量的绝对值交给Header, 就可以实现放大效果
            if (headView.getHeight() <= mDrawableHeight) {
                // 高度不超出图片最大高度时,才让其生效
                int newHeight = (int) (headView.getHeight() + Math.abs(deltaY / 3.0f));//这里除以3是为了达到视差的效果
                headView.getLayoutParams().height = newHeight;
                //此方法必须调用,调用后会重新调用onMeasure和onLayout方法进行测量和定位
                headView.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                // 执行回弹动画, 方式一: 属性动画\值动画
                //获取ImageView在松手时的高度
                int currHeight = headView.getHeight();
                // 从当前高度mHeaderIv.getHeight(), 执行动画到原始高度mOriginalHeight
                ValueAnimator animator = ValueAnimator.ofInt(currHeight, mOriginalHeight);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        headView.getLayoutParams().height = value;
                        //此方法必须调用,调用后会重新调用onMeasure和onLayout方法进行测量和定位
                        headView.requestLayout();
                    }
                });
                animator.setDuration(300);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();

                //方式二,通过自定义动画
                /*ResetAnimation animation = new ResetAnimation(mHeaderIv, mHeaderIv.getHeight(), mOriginalHeight);
                startAnimation(animation);*/
                break;
        }
        return super.onTouchEvent(ev);
    }
}
