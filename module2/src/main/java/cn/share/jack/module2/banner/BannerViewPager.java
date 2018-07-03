package cn.share.jack.module2.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by jack on 2018/5/7
 */

public class BannerViewPager extends ViewPager {

    private BannerAdapter mAdapter;
    private WeakHandler mHandler;
    private final int SCROLL_MSG = 0x0001;
    private final long SCROLL_DELAY_TIME = 3500;  //页面切换间隔时间
    private BannerScroller mScroller;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new WeakHandler(this);
        mScroller = new BannerScroller(context);
        try {
            //改变ViewPager动画切换速率，通过去改变ViewPager中的mScroller
            Field field = ViewPager.class.getDeclaredField("mScroller");
            //设置强制改变private属性
            field.setAccessible(true);
            //第一个参数object为当前属性在哪个类中，第二个参数object表示要设置的值（设置ViewPager中的Scroller属性为我们自定义的）
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScrollDuration(int scrollDuration) {
        mScroller.setScrollDuration(scrollDuration);
    }

    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        setAdapter(new BannerPagerAdapter());
    }

    public void startRoll() {
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, SCROLL_DELAY_TIME);
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View bannerView = mAdapter.getView(position % mAdapter.getCount());
            //添加到ViewPager
            container.addView(bannerView);
            return bannerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }

    /**
     * Activity销毁时会调用这个方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mHandler) {
            mHandler.removeMessages(SCROLL_MSG);
            mHandler = null;
        }
    }

    private static class WeakHandler extends Handler {

        WeakReference<BannerViewPager> weakReference;

        WeakHandler(BannerViewPager bannerViewPager) {
            this.weakReference = new WeakReference<>(bannerViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BannerViewPager bannerViewPager = weakReference.get();
            if (msg.what == bannerViewPager.SCROLL_MSG) {
                //切换到下一页
                bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
                bannerViewPager.startRoll();
            }
        }
    }
}