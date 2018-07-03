package cn.share.jack.test;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jack on 2018/3/8
 */

public class CustomNumberView extends View {

    public CustomNumberView(Context context) {
        super(context);
    }

    public CustomNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
