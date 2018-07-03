package cn.share.jack.test.base;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.share.jack.module2.base.FRFragment;

/**
 * Created by jack on 2018/6/7
 */
public abstract class BaseViewPagerFragment extends FRFragment {

    protected static final String LOADING_TAG = "loading_tag";
    protected static final String ERROR_TAG = "error_tag";

    private boolean isFragmentVisible;   //Fragment是否可见
    protected boolean isFirstLoadData;  //是否第一次加载数据

    private boolean isInitTitleBar;

    /**
     * onViewReallyCreated才是真正创建了View
     */
    protected void onViewReallyCreated(View view) {
        super.onViewReallyCreated(view);
        //可见，但是并没有加载过
        if (isFragmentVisible && !isFirstLoadData) {
            isInitTitleBar = initTitleBar();
            showLoadingLayout();
            loadData(true);
        }
    }

    private void addViewToDecor(View view) {
        if (null != rootView()) {
            removeChildView((ViewGroup) rootView(), false, isInitTitleBar ? 1 : 0);
            ((ViewGroup) rootView()).addView(view, isInitTitleBar ? 1 : 0);
            if (rootView() instanceof RelativeLayout) {
                if (((ViewGroup) rootView()).getChildCount() > 1) {
                    View titleView = ((ViewGroup) rootView()).getChildAt(0);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, titleView.getId());
                    view.setLayoutParams(params);
                }
            }
        }
    }

    private void removeChildView(ViewGroup rootView, boolean isVisible, int start) {
        if (null == rootView) {
            return;
        }
        int childCount = rootView.getChildCount();
        if (childCount > 0) {
            for (int i = start; i < childCount; i++) {
                View view = rootView.getChildAt(i);
                if (null != view) {
                    Object object = view.getTag();
                    if (null != object && !TextUtils.isEmpty(object.toString())) {
                        if (object.toString().equals(LOADING_TAG) || object.toString().equals(ERROR_TAG)) {
                            rootView.removeViewAt(i);
                            childCount--;
                            i--;
                        } else {
                            view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
                        }
                    } else {
                        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
                    }
                }
            }
        }
    }

    private void removeChildView(ViewGroup rootView, boolean isVisible) {
        removeChildView(rootView, isVisible, 0);
    }

    public void showContentView() {
        removeChildView((ViewGroup) rootView(), true);
    }

    public void showLoadingLayout() {
        if (null != getLoadingView() && needShowLoadingView()) {
            addViewToDecor(getLoadingView());
        }
    }

    public void showErrorLayout() {
        if (null != getErrorView()) {
            addViewToDecor(getErrorView());
        }
    }

    protected boolean initTitleBar() {
        return false;
    }

    /**
     * 懒加载
     */
    private void loadData(boolean isVisible) {
        if (isVisible) {
            lazyLoad();
            isFirstLoadData = true;
        } else {
            stopLoad();
        }
    }

    protected void lazyLoad() {

    }

    protected void stopLoad() {

    }

    protected View getLoadingView() {
        return null;
    }

    protected View getErrorView() {
        return null;
    }

    protected boolean needShowLoadingView() {
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (null == rootView()) {
            return;
        }
        onFragmentHiddenChanged(isVisibleToUser);
        //由不可见变为可见的过程，并且没有加载过数据
        if (!isFirstLoadData && isFragmentVisible) {
            isInitTitleBar = initTitleBar();
            showLoadingLayout();
            loadData(true);
            return;
        }
        //已经加载过，且由可见变为不可见的过程
        if (isFragmentVisible) {
            loadData(false);
            isFragmentVisible = false;
        }
    }

    protected void onFragmentHiddenChanged(boolean isHidden) {

    }
}
