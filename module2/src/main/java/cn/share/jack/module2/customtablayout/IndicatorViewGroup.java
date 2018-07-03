package cn.share.jack.module2.customtablayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by jack on 2018/4/26
 */

public class IndicatorViewGroup extends FrameLayout {

    //指示器条目容器
    private LinearLayout mIndicatorGroup;

    private View mBottomTrackView;

    //指示器宽度
    private int mItemWidth;

    private LayoutParams mParams;

    private int mInitLeftMargin;

    public IndicatorViewGroup(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);
    }

    /**
     * 添加指示器 view
     *
     * @param itemView
     */
    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    public View getItemViewAt(int position) {
        return mIndicatorGroup.getChildAt(position);
    }

    /**
     * 设置底部View
     *
     * @param bottomTrackView
     * @param itemWidth
     */
    public void setBottomTrackView(View bottomTrackView, int itemWidth) {
        if (null == bottomTrackView) {
            return;
        }
        mItemWidth = itemWidth;
        mBottomTrackView = bottomTrackView;
        addView(mBottomTrackView);

        //添加底部跟踪View
        mParams = (LayoutParams) mBottomTrackView.getLayoutParams();
        mParams.gravity = Gravity.BOTTOM;
        int trackViewWidth = mParams.width;
        if (mParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            trackViewWidth = mItemWidth;
        }
        if (trackViewWidth > mItemWidth) {
            trackViewWidth = mItemWidth;
        }
        mParams.width = trackViewWidth;

        //确保bottomView在中间
        mInitLeftMargin = mParams.leftMargin = (mItemWidth - trackViewWidth) / 2;
        mParams.leftMargin = mInitLeftMargin;
    }

    //同步滚动底部View  滑动的时候--->利用leftMargin
    public void scrollBottomTrackView(int position, float positionOffset) {
        if (null==mBottomTrackView){
            return;
        }
        int leftMargin = (int) ((position + positionOffset) * mItemWidth);
        mParams.leftMargin = leftMargin + mInitLeftMargin;
        mBottomTrackView.setLayoutParams(mParams);
    }

    //同步滚动底部View  点击的时候--->利用leftMargin
    public void scrollBottomTrackView(int position) {
        if (null==mBottomTrackView){
            return;
        }
        int finalLeftMargin = position * mItemWidth + mInitLeftMargin;
        int currentLeftMargin = mParams.leftMargin;
        int distance = finalLeftMargin - currentLeftMargin;

        ValueAnimator animator = ObjectAnimator.ofFloat(currentLeftMargin, finalLeftMargin)
                .setDuration((long) (Math.abs(distance) * 0.5f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentLeftMargin = (float) animation.getAnimatedValue();
                mParams.leftMargin = (int) currentLeftMargin;
                mBottomTrackView.setLayoutParams(mParams);
            }
        });
        //设置一个减速的差值器
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    public void requestItemLayout() {
        mIndicatorGroup.requestLayout();
    }
}