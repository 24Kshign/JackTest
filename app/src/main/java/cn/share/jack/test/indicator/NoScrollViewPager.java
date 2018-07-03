package cn.share.jack.test.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jack on 2018/6/19
 */
public class NoScrollViewPager extends ViewPager {

    private boolean isScroll;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }
    }
}
