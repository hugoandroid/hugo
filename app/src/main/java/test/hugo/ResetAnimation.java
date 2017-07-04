package test.hugo;

import android.animation.IntEvaluator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * 自定义动画
 * Created by mChenys on 2015/12/24.
 */
public class ResetAnimation extends Animation {
    private final ImageView headerIv; //要执行动画的目标ImageView
    private final int startHeight;//执行动画的开始时的高度
    private final int endHeight;//执行动画结束时的高度
    private IntEvaluator mEvaluator; //整型估值器

    /**
     * 构造方法初始化
     *
     * @param headerIv    应用动画的目标控件
     * @param startHeight 开始的高度
     * @param endHeight   结束的高度
     */
    public ResetAnimation(ImageView headerIv, int startHeight, int endHeight) {
        this.headerIv = headerIv;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
        //定义一个int类型的类型估值器,用于获取实时变化的高度值
        mEvaluator = new IntEvaluator();
        //设置动画持续时间
        setDuration(500);
        //设置插值器
        setInterpolator(new OvershootInterpolator());
    }

    /**
     * 在指定的时间内一直执行该方法，直到动画结束
     * interpolatedTime：0-1  标识动画执行的进度或者百分比
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int currHeight = mEvaluator.evaluate(interpolatedTime, startHeight, endHeight);
        //通过LayoutParams不断的改变其高度
        headerIv.getLayoutParams().height = currHeight;
        //此方法必须调用,调用后会重新调用onMeasure和onLayout方法进行测量和定位
        headerIv.requestLayout();
    }
}