package cn.share.jack.test.waveview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jack on 2018/2/8
 */

public class CustomWaveView extends View {

    private int mWaveCount;
    private float mWaveWidth;
    private float mWaveHeight;
    private Paint mPaint;
    private Path path;

    public CustomWaveView(Context context) {
        this(context, null);
    }

    public CustomWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWaveHeight = 100;
        mWaveWidth = getMeasuredWidth();

        mWaveCount = (int) (getMeasuredWidth() / mWaveWidth + 0.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(0, getMeasuredHeight() / 2);

        path.quadTo(mWaveWidth / 4, getMeasuredHeight() / 2 - mWaveHeight, mWaveWidth / 2, getMeasuredHeight() / 2);

        path.quadTo(mWaveWidth * 3 / 4, getMeasuredHeight() / 2 + mWaveHeight, mWaveWidth, getMeasuredHeight() / 2);
        path.lineTo(getMeasuredHeight(), getMeasuredHeight());
        path.lineTo(0, getMeasuredHeight());
        path.close();

        canvas.drawPath(path, mPaint);
    }
}
