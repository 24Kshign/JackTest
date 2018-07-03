package cn.share.jack.test.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.share.jack.test.R;

/**
 * Created by jack on 2018/6/14
 */
public abstract class BaseFragment extends BaseViewPagerFragment {

    private View mErrorView;
    private View mLoadingView;

    @Override
    protected void onViewReallyCreated(View view) {
        if (needShowLoadingView()) {
            mLoadingView = LayoutInflater.from(thisActivity()).inflate(R.layout.layout_loading, null);
            mLoadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mLoadingView.setTag(LOADING_TAG);
        }
        mErrorView = LayoutInflater.from(thisActivity()).inflate(R.layout.layout_error, null);
        mErrorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mErrorView.setTag(ERROR_TAG);
        super.onViewReallyCreated(view);
    }

    @Override
    protected View getLoadingView() {
        return mLoadingView;
    }

    @Override
    protected View getErrorView() {
        return mErrorView;
    }
}