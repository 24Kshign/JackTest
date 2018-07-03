package cn.share.jack.test.love;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import cn.share.jack.test.R;

/**
 * Created by jack on 2018/2/5
 */

public class BesselLoveUIComponent extends RelativeLayout {

    private int[] mImageRes = {R.mipmap.icon_my_coupon, R.mipmap.icon_setting, R.mipmap.icon_my_loan};
    private Random mRandom;
    private int mWidth;
    private int mHeight;
    private int mDrawableWidth;
    private int mDrawableHeight;

    private Interpolator[] mInterpolator;

    public BesselLoveUIComponent(Context context) {
        this(context, null);
    }

    public BesselLoveUIComponent(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesselLoveUIComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRandom = new Random();
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_my_coupon);
        //获取图片宽高
        mDrawableWidth = drawable.getIntrinsicWidth();
        mDrawableHeight = drawable.getIntrinsicHeight();
        mInterpolator = new Interpolator[]{new AccelerateDecelerateInterpolator(),
                new AccelerateInterpolator(), new LinearInterpolator(), new DecelerateInterpolator()};
    }

    public void addLove() {
        final ImageView image = new ImageView(getContext());
        image.setImageResource(mImageRes[mRandom.nextInt(mImageRes.length - 1)]);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        image.setLayoutParams(params);

        addView(image);

        AnimatorSet animatorSet = getAnimatorSet(image);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(image);   //动画结束之后移除view，节省资源
            }
        });
        animatorSet.start();
    }

    private AnimatorSet getAnimatorSet(ImageView iv) {
        AnimatorSet allAnimatorSet = new AnimatorSet();

        //添加放大
        AnimatorSet innerAnimator = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0.3f, 1.0f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 0.3f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv, "scaleY", 0.3f, 1.0f);
        innerAnimator.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        innerAnimator.setDuration(350);
        innerAnimator.start();

        //运动路径
        allAnimatorSet.playSequentially(innerAnimator, getBesselAnimator(iv));
        return allAnimatorSet;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取屏幕的宽高
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    private Animator getBesselAnimator(final ImageView iv) {

        PointF point0 = new PointF(mWidth / 2 - mDrawableWidth / 2, mHeight - mDrawableHeight);
        //确保p1点的y值比p2点的y值小
        PointF point1 = getPoint(1);
        PointF point2 = getPoint(2);
        PointF point3 = new PointF(mRandom.nextInt(mWidth) - mDrawableWidth / 2, 0);


        LoveTypeEvaluator typeEvaluator = new LoveTypeEvaluator(point1, point2);
        //第一个参数是TypeEvaluator，第二个参数是p0，第二个参数是p1
        ValueAnimator besselAnimator = ValueAnimator.ofObject(typeEvaluator, point0, point3);
        besselAnimator.setDuration(3000);
        //加差值器
        besselAnimator.setInterpolator(mInterpolator[mRandom.nextInt(mInterpolator.length - 1)]);
        besselAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                iv.setX(pointF.x);
                iv.setY(pointF.y);
                //透明度
                float t = animation.getAnimatedFraction();
                iv.setAlpha(1 - t + 0.2f);
            }
        });
        return besselAnimator;
    }

    private PointF getPoint(int index) {
        return new PointF(mRandom.nextInt(mWidth) - mDrawableWidth / 2, mRandom.nextInt(mHeight / 2) + (index - 1) * mHeight / 2);
    }
}
