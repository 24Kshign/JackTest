package cn.share.jack.module2.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.share.jack.module2.R;
import cn.share.jack.module2.banner.indicator.DotIndicatorView;

/**
 * Created by jack on 2018/5/7
 */

public class BannerView extends RelativeLayout {

    private static final int INDICATOR_GRAVITY_LEFT = 0;
    private static final int INDICATOR_GRAVITY_CENTER = 1;
    private static final int INDICATOR_GRAVITY_RIGHT = 2;

    private LinearLayout mDotContainer;
    private BannerViewPager mBannerVp;
    private Context mContext;
    private BannerAdapter mAdapter;

    private Drawable mIndicatorFocusDrawable;   //选中样式
    private Drawable mIndicatorNormalDrawable;  //正常样式

    private int mIndicatorGravity = -1;//点的位置默认左边
    private int mDotSize;//点的大小，也就是直径
    private int mIndicatorDistance;//点之间的间距
    private int mIndicatorType;
    private int mCurrentPosition = 0;  //当前准确位置

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflate(context, R.layout.layout_banner, this);
        initView();
        initAttribute(attrs);
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mIndicatorGravity = typedArray.getInt(R.styleable.BannerView_indicatorGravity, 0);
        mIndicatorFocusDrawable = typedArray.getDrawable(R.styleable.BannerView_indicatorFocus);
        if (mIndicatorFocusDrawable == null) {
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorNormalDrawable = typedArray.getDrawable(R.styleable.BannerView_indicatorNormal);
        if (mIndicatorNormalDrawable == null) {
            mIndicatorNormalDrawable = new ColorDrawable(Color.BLACK);
        }
        mDotSize = (int) typedArray.getDimension(R.styleable.BannerView_dotSize, 6);
        mIndicatorDistance = (int) typedArray.getDimension(R.styleable.BannerView_indicatorDistance, 3);
        mIndicatorType = typedArray.getInt(R.styleable.BannerView_indicatorType, 0);
        typedArray.recycle();
    }

    private void initView() {
        mBannerVp = findViewById(R.id.lb_banner_viewpager);
        mDotContainer = findViewById(R.id.lb_ll_dot_container);
    }

    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        mIndicatorType = adapter.getCount() == 1 ? 0 : mIndicatorType;
        //初始化指示器
        if (mIndicatorType == 1) {
            initDotIndicator();
        }
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (mIndicatorType == 1) {
                    //监听当前选中的位置
                    pageSelected(position);
                }
            }
        });
    }

    private void pageSelected(int position) {
        //设置上一个选中的点为正常
        DotIndicatorView oldIndicatorView = (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mIndicatorNormalDrawable);
        mCurrentPosition = position % mAdapter.getCount();
        //设置选中的点
        DotIndicatorView currentIndicatorView = (DotIndicatorView) mDotContainer.getChildAt(mCurrentPosition);
        currentIndicatorView.setDrawable(mIndicatorFocusDrawable);
    }

    private void initDotIndicator() {
        mDotContainer.setGravity(getDotGravity());
        int dotCount = mAdapter.getCount();
        for (int i = 0; i < dotCount; i++) {
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip2px(mDotSize), dip2px(mDotSize));
            layoutParams.leftMargin = layoutParams.rightMargin = dip2px(mIndicatorDistance);
            indicatorView.setLayoutParams(layoutParams);
            indicatorView.setDrawable(i == 0 ? mIndicatorFocusDrawable : mIndicatorNormalDrawable);
            mDotContainer.addView(indicatorView);
        }
    }

    public void setIndicatorFocusDrawable(Drawable indicatorFocusDrawable) {
        this.mIndicatorFocusDrawable = indicatorFocusDrawable;
    }

    public void setIndicatorNormalDrawable(Drawable indicatorNormalDrawable) {
        this.mIndicatorNormalDrawable = indicatorNormalDrawable;
    }

    //设置指示器位置
    public void setIndicatorGravity(int indicatorGravity) {
        mIndicatorGravity = indicatorGravity;
    }

    //设置点的大小，圆的直径(dp)
    public void setDotSize(int dotSize) {
        mDotSize = dotSize;
    }

    public void setIndicatorDistance(int indicatorDistance) {
        mIndicatorDistance = indicatorDistance;
    }

    private int dip2px(float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    public void startRoll() {
        mBannerVp.startRoll();
    }

    private int getDotGravity() {
        switch (mIndicatorGravity) {
            case 0:
                return Gravity.START;
            case 1:
                return Gravity.CENTER;
            case 2:
                return Gravity.END;
        }
        return Gravity.CENTER;
    }
}