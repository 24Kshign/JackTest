package cn.share.jack.module2.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by jack on 2018/6/13
 */
public abstract class FRBaseActivity extends FragmentActivity {

    protected static final String LOADING_TAG = "loading_tag";
    protected static final String ERROR_TAG = "error_tag";

    private boolean isInitTitleBar;

    protected boolean isLoadDataSuccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (layoutRes() <= 0) {
            throw new NullPointerException("layoutRes should not be null");
        }
        setContentView(layoutRes());
        initView();
        isInitTitleBar = initTitleBar();
        showLoadingLayout();
        initData();
    }

    protected boolean initTitleBar() {
        return false;
    }

    protected abstract int layoutRes();

    protected ViewGroup getRootLayout() {
        return null;
    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected View getLoadingView() {
        return null;
    }

    protected View getErrorView() {
        return null;
    }

    private void addViewToDecor(View view) {
        if (null == view || isLoadDataSuccess) {
            return;
        }
        ViewGroup rootView;
        if (null != getRootLayout()) {
            rootView = getRootLayout();
            removeChildView(rootView, false, isInitTitleBar ? 0 : 1);
            rootView.addView(view, isInitTitleBar ? 0 : 1);
            if (rootView instanceof RelativeLayout) {
                if (rootView.getChildCount() > 1) {
                    View titleView = rootView.getChildAt(0);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, titleView.getId());
                    view.setLayoutParams(params);
                }
            }
        } else {
            rootView = (ViewGroup) ((ViewGroup) getWindow().getDecorView()).getChildAt(0);
            removeChildView(rootView, false);
            if (isInitTitleBar) {
                rootView.addView(view, 1);
            } else {
                //TODO 这种情况整个页面会被loading和error页面遮住（暂时没有找到比较好的方案解决）
                rootView.addView(view, 0);
            }
        }
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

    public void showContentView() {
        ViewGroup rootView;
        if (null != getRootLayout()) {
            rootView = getRootLayout();
            removeChildView(rootView, true);
        } else {
            rootView = (ViewGroup) ((ViewGroup) getWindow().getDecorView()).getChildAt(0);
            removeChildView(rootView, true);
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

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (Build.VERSION.SDK_INT >= 24) {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    protected Activity thisActivity() {
        return this;
    }

    protected boolean needShowLoadingView() {
        return true;
    }
}
