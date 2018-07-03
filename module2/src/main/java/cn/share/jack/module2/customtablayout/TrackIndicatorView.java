package cn.share.jack.module2.customtablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import cn.share.jack.module2.R;

/**
 * Created by jack on 2018/4/26
 */

public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {

    private IndicatorAdapter mAdapter;

    //指示器条目容器（因为HorizontalScrollView内部只能有一个孩子）
    private IndicatorViewGroup mIndicatorGroup;
    //一屏显示多少个可见的item
    private int mTabVisibleNum;
    //单个item的width
    private int mItemWidth;
    private ViewPager mViewPager;
    //指示器当前位置
    private int mCurrentPosition;

    private boolean mIsExecuteScroll;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNum = array.getInteger(R.styleable.TrackIndicatorView_tabVisibleNum, 0);
        array.recycle();
        mIndicatorGroup = new IndicatorViewGroup(context);
        addView(mIndicatorGroup);
    }

    //设置一个适配器
    public void setAdapter(IndicatorAdapter adapter) {
        if (null == adapter) {
            throw new NullPointerException("adapter is null");
        }

        mAdapter = adapter;

        //动态添加View  1、获取有多少数据
        int itemCount = mAdapter.getCount();
        //2、循环添加itemView
        for (int i = 0; i < itemCount; i++) {
            View itemView = mAdapter.getView(i, mIndicatorGroup);
            mIndicatorGroup.addItemView(itemView);
            if (null != mViewPager) {
                //指示器的点击事件
                switchItemViewClick(itemView, i);
            }
        }
        //默认第一个位置高亮
        mAdapter.highLightIndicator(mIndicatorGroup.getItemViewAt(0));
    }

    private void switchItemViewClick(View itemView, final int position) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position, false);
                smoothScrollIndicatorTo(position);
                mIndicatorGroup.scrollBottomTrackView(position);
            }
        });
    }

    /**
     * 点击的时候指示器变动
     *·
     * @param position
     */
    private void smoothScrollIndicatorTo(int position) {
        //当前位置总的位置
        float totalScroll = position * mItemWidth;
        //左边的偏移
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        //最终需要滚动的距离
        final int finalScroll = (int) (totalScroll - offsetScroll);
        //滚动
        smoothScrollTo(finalScroll, 0);
    }

    /**
     * 设置一个适配器并且当前指示器居中
     *
     * @param adapter
     * @param viewPager
     */
    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager) {
        if (null == viewPager) {
            throw new NullPointerException("viewpager is null");
        }
        mViewPager = viewPager;
        setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //1、指定item的宽度
            mItemWidth = getItemWidth();
            //2、循环指定item的宽度
            for (int i = 0; i < mAdapter.getCount(); i++) {
                mIndicatorGroup.getItemViewAt(i).getLayoutParams().width = mItemWidth;
            }
            mIndicatorGroup.requestItemLayout();
            mIndicatorGroup.setBottomTrackView(mAdapter.getBottomTrackView(), mItemWidth);
        }
    }

    /**
     * 获取item的宽度
     *
     * @return
     */
    private int getItemWidth() {
        int parentWidth = getWidth();
        //有指定宽度
        if (mTabVisibleNum > 0) {
            return parentWidth / mTabVisibleNum;
        }
        int itemWidth;
        int maxItemWidth = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            int currentItemWidth = mIndicatorGroup.getItemViewAt(i).getWidth();
            maxItemWidth = Math.max(currentItemWidth, maxItemWidth);
        }
        itemWidth = maxItemWidth;
        int allWidth = itemWidth * mAdapter.getCount();
        //宽度就是获取最宽的
        if (allWidth < parentWidth) {
            itemWidth = parentWidth / mAdapter.getCount();
        }
        return itemWidth;
    }

    /**
     * 设置一屏显示多少个指示器
     *
     * @param num
     */
    public void setTabVisibleNum(int num) {
        mTabVisibleNum = num;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mIsExecuteScroll) {
            //滚动的时候会不断调用
            scrollCurrentIndicator(position, positionOffset);
            mIndicatorGroup.scrollBottomTrackView(position, positionOffset);
        }
    }

    /**
     * 当前指示器居中
     *
     * @param position
     * @param positionOffset
     */
    private void scrollCurrentIndicator(int position, float positionOffset) {
        //当前位置总的位置
        float totalScroll = (position + positionOffset) * mItemWidth;
        //左边的偏移
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        //最终需要滚动的距离
        final int finalScroll = (int) (totalScroll - offsetScroll);
        //滚动
        scrollTo(finalScroll, 0);
    }

    @Override
    public void onPageSelected(int position) {
        mAdapter.resetIndicator(mIndicatorGroup.getItemViewAt(mCurrentPosition));
        mCurrentPosition = position;
        mAdapter.highLightIndicator(mIndicatorGroup.getItemViewAt(mCurrentPosition));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            mIsExecuteScroll = false;
        } else if (state == 1) {
            mIsExecuteScroll = true;
        }
    }
}