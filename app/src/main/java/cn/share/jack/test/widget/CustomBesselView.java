package cn.share.jack.test.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by jack on 2018/2/5
 */

public class CustomBesselView extends View {

    /**
     * 1、有两个圆，一个是固定位置固定但是半径会变化（两个圆之间的距离越远半径就越小），还有一个就是拖拽圆，半径是不变的，位置是跟着手指移动
     * 2、在两个圆中间会有一个粘性的不规则的图像（贝塞尔曲线）
     */

    private PointF mFixedPoint;   //固定圆
    private PointF mDragPoint;   //拖拽圆

    private int mDragRadius = 10;  //拖拽圆的半径
    private int mMaxFixedRadius = 8;//固定圆的初始半径
    private int mFixedRadius;    //固定圆的变化半径
    private int mMinFixedRadius = 3;

    private Paint mPaint;   //画笔
    private boolean isDisappear;   //手松开时，是否消失点

    private ValueAnimator animatorX;
    private ValueAnimator animatorY;
    private float mDragResetX;
    private float mDragResetY;

    public CustomBesselView(Context context) {
        this(context, null);
    }

    public CustomBesselView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBesselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragRadius = dip2px(mDragRadius);
        mMaxFixedRadius = dip2px(mMaxFixedRadius);
        mMinFixedRadius = dip2px(mMinFixedRadius);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画两个圆
        //拖拽圆
        if (null == mDragPoint || null == mFixedPoint) {
            return;
        }
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);

        //贝塞尔曲线路径
        Path besselPath = getBesselPath();
        if (null != besselPath && !isDisappear) {
            canvas.drawCircle(mFixedPoint.x, mFixedPoint.y, mFixedRadius, mPaint);

            canvas.drawPath(besselPath, mPaint);
        }
    }

    private Path getBesselPath() {
        //固定圆
        double distance = getTwoPointDistance(mDragPoint, mFixedPoint);
        mFixedRadius = (int) (mMaxFixedRadius - distance / 14);
        if (mFixedRadius < mMinFixedRadius) {
            isDisappear = true;
            return null;
        }
        /**
         * 详情参见drawable中的icon_bessel.png
         */
        Path path = new Path();
        float dy = mDragPoint.y - mFixedPoint.y;
        float dx = mDragPoint.x - mFixedPoint.x;
        float tanA = dy / dx;   //角a的正切值
        double aTan = Math.atan(tanA);   //角a

        //p0
        float p0x = (float) (mFixedPoint.x + mFixedRadius * Math.sin(aTan));
        float p0y = (float) (mFixedPoint.y - mFixedRadius * Math.cos(aTan));

        //p1
        float p1x = (float) (mDragPoint.x + mDragRadius * Math.sin(aTan));
        float p1y = (float) (mDragPoint.y - mDragRadius * Math.cos(aTan));

        //p2
        float p2x = (float) (mDragPoint.x - mDragRadius * Math.sin(aTan));
        float p2y = (float) (mDragPoint.y + mDragRadius * Math.cos(aTan));

        //p3
        float p3x = (float) (mFixedPoint.x - mFixedRadius * Math.sin(aTan));
        float p3y = (float) (mFixedPoint.y + mFixedRadius * Math.cos(aTan));

        //拼装path
        //移动到第一条曲线的起始点
        path.moveTo(p0x, p0y);
        //控制点（取的是两个圆心的中心点）
        PointF controllerPoint = getControllerPoint();
        //画第一条
        path.quadTo(controllerPoint.x, controllerPoint.y, p1x, p1y);
        //移动到第二条曲线的起始点
        path.lineTo(p2x, p2y);
        //画第二条
        path.quadTo(controllerPoint.x, controllerPoint.y, p3x, p3y);
        path.close();
        return path;
    }

    private PointF getControllerPoint() {
        return new PointF((mDragPoint.x + mFixedPoint.x) / 2, (mDragPoint.y + mFixedPoint.y) / 2);
    }

    private double getTwoPointDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                isDisappear = false;
                float downX = event.getX();
                float downY = event.getY();
                initPoint(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                updateDragPoint(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:
                if (isDisappear) {
                    //TODO 将圆消失
                } else {
                    //将圆还原
                    resetPoint(event.getX(), event.getY());
                }
                break;
        }
        invalidate();
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void resetPoint(float upX, float upY) {
        animatorX = ValueAnimator.ofFloat(upX, mFixedPoint.x);
        animatorY = ValueAnimator.ofFloat(upY, mFixedPoint.y);
        animatorX.removeAllUpdateListeners();
        animatorY.removeAllUpdateListeners();
        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDragResetX = (float) animation.getAnimatedValue();
            }
        });
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDragResetY = (float) animation.getAnimatedValue();
                mDragPoint.x = mDragResetX;
                mDragPoint.y = mDragResetY;
                postInvalidate();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();
    }

    private void updateDragPoint(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
    }

    //初始化两个圆
    private void initPoint(float downX, float downY) {
        mFixedPoint = new PointF(downX, downY);
        mDragPoint = new PointF(downX, downY);
    }

    public void cancelAnimator() {
        if (null != animatorX) {
            animatorX.cancel();
            animatorX = null;
        }
        if (null != animatorY) {
            animatorY.cancel();
            animatorY = null;
        }
    }
}
