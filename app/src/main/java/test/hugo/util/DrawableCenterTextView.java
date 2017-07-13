package test.hugo.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * drawableLeft与文本一起居中显示
 * @author Hugo
 * modify time 2017年7月13日11:11:01 add drawableTop support
 */
public class DrawableCenterTextView extends android.support.v7.widget.AppCompatTextView {

    public DrawableCenterTextView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables[0] != null) {
            setGravity(Gravity.CENTER_VERTICAL);
            float textWidth = getPaint().measureText(getText().toString());
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = drawables[0].getIntrinsicWidth();
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }else if(drawables[1] != null){//drawableTop
            setGravity(Gravity.CENTER_HORIZONTAL);
            float textHeight = getTextSize()+getCompoundDrawablePadding();
            int drawableHeight = drawables[1].getIntrinsicHeight();
            float bodyHeight = drawableHeight + textHeight;
            canvas.translate(0,(getHeight() - bodyHeight) / 2);
        }
        super.onDraw(canvas);
    }
}