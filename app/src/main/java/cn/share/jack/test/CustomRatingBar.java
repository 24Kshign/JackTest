package cn.share.jack.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jack on 2018/2/27
 */

public class CustomRatingBar extends View {

    private Paint mPaint;

    public CustomRatingBar(Context context) {
        this(context, null);
    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        getWidth();
        getMeasuredWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "onTouchEvent --> "+event.getAction());
        return false;
    }
}
