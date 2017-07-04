package test.hugo.swipe;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lovejjfg on 2015/11/8.
 */
public class SlideToFinishLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mContentView;
    private int mContentWidth;
    private BaseSlideFinishActivity activity;
    private int mMoveLeft;
    private boolean isClose;
    private CallBack mCallBack;

    public SlideToFinishLayout(Context context) {
        this(context, null);
    }

    public SlideToFinishLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideToFinishLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (context instanceof BaseSlideFinishActivity) {
            activity = (BaseSlideFinishActivity) context;
        }
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mContentView;//只有mContentView可以移动
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                mMoveLeft = left;
                if (isClose && (left == mContentWidth)) {
                    if (mCallBack != null) {
                        mCallBack.onFinish();
                    }
                    if (null != activity) {
                        activity.finishSelf();
//                    } else {
//                        throw new RuntimeException("请使用BaseSlideFinishActivity!!");
//                        activity.finish();
//                        activity.overridePendingTransition(0, 0);
                    }

                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (mMoveLeft >= (mContentWidth / 2)) {
                    //滑动超过屏幕的一半，那么就可以判断为true了!
                    isClose = true;
                    mViewDragHelper.settleCapturedViewAt(mContentWidth, releasedChild.getTop());
                } else {
                    mViewDragHelper.settleCapturedViewAt(0, releasedChild.getTop());
                }
                invalidate();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                //这样的话，页面就只能向右滑动了！
                return Math.min(mContentWidth, Math.max(left, 0));
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                //一定要重写这个方法，返回>0的值，不然当子View可以消耗触摸事件时就会无法移动。
                return mContentWidth;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                //在里面处理我们需要实现边界滑动有效的view
                mViewDragHelper.captureChildView(mContentView, pointerId);
            }
        });

        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
        //这里一定要记得返回true；
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(mContentView !=null)
            mContentWidth = mContentView.getWidth();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //该布局中应该只有一个子布局，所有的其他布局填充到这个子布局中。如果是代码中构造，不会走
        mContentView = getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mContentView == null)
            mContentView = getChildAt(0);
        Log.e("onSizeChanged",w+" "+h +" " +oldw +"    "+oldh);
    }

    public void SetCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack {

        void onFinish();
    }
}
