package cn.share.jack.module2.refreshload.refresh;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jack on 2018/6/8
 */
public abstract class RefreshViewCreator {

    /**
     * 获取下拉刷新的View
     */
    public abstract View getRefreshView(Context context, ViewGroup parent);

    /**
     * 正在下拉
     *
     * @param currentDragHeight   当前拖动高度
     * @param refreshViewHeight   总体刷新高度
     * @param currentRefreshState 当前状态
     */
    public abstract void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshState);

    /**
     * 正在刷新中...
     */
    public abstract void onRefreshing();

    /**
     * 停止刷新
     */
    public abstract void onStopRefresh();

    public abstract void cancelRefresh();
}
