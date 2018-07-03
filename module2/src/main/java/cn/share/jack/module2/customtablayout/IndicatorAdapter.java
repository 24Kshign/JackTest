package cn.share.jack.module2.customtablayout;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jack on 2018/4/26
 */

public abstract class IndicatorAdapter {

    /**
     * 获取总共有多少条数据
     *
     * @return
     */
    public abstract int getCount();

    /**
     * 根据当前位置获取
     *
     * @param position
     * @param parent
     * @return
     */
    public abstract View getView(int position, ViewGroup parent);

    /**
     * 当前指示器高亮
     *
     * @param view
     */
    public void highLightIndicator(View view) {

    }

    /**
     * 重制指示器
     *
     * @param view
     */
    public void resetIndicator(View view) {

    }

    public View getBottomTrackView() {
        return null;
    }
}