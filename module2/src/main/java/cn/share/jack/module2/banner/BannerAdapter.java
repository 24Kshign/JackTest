package cn.share.jack.module2.banner;

import android.view.View;

/**
 * Created by jack on 2018/5/7
 */

public abstract class BannerAdapter {

    /**
     * 根据位置获取ViewPager的子View
     *
     * @param position
     * @return
     */
    public abstract View getView(int position);

    /**
     * 获取轮播的数量
     * @return
     */
    public abstract int getCount();
}
