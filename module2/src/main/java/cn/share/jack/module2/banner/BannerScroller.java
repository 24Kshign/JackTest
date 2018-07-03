package cn.share.jack.module2.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by jack on 2018/5/7
 */

public class BannerScroller extends Scroller {

    //默认ViewPager切换动画持续时间
    private int mScrollDuration = 950;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void setScrollDuration(int scrollDuration) {
        this.mScrollDuration = scrollDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }
}
