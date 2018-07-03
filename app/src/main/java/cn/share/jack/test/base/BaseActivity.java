package cn.share.jack.test.base;

import android.accounts.NetworkErrorException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.ConnectException;
import java.net.UnknownHostException;

import cn.share.jack.module2.base.FRBaseActivity;
import cn.share.jack.test.R;

/**
 * Created by jack on 2018/6/13
 */
public abstract class BaseActivity extends FRBaseActivity {

    private View mErrorView;
    private View mLoadingView;

    @Override
    protected void initView() {
        super.initView();
        if (needShowLoadingView()) {
            mLoadingView = LayoutInflater.from(thisActivity()).inflate(R.layout.layout_loading, null);
            mLoadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mLoadingView.setTag(LOADING_TAG);
        }
        mErrorView = LayoutInflater.from(thisActivity()).inflate(R.layout.layout_error, null);
        mErrorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mErrorView.setTag(ERROR_TAG);
    }

    @Override
    protected View getLoadingView() {
        return mLoadingView;
    }

    @Override
    protected View getErrorView() {
        return mErrorView;
    }

    public void showErrorLayout(Throwable t) {
        if (null != mErrorView) {
            TextView tvError = mErrorView.findViewById(R.id.le_tv_error);
            if (null != t && t instanceof NetworkErrorException
                    || t instanceof UnknownHostException
                    || t instanceof ConnectException) {
                tvError.setText("网络异常，请点击重试...");
            } else {
                tvError.setText("数据加载出错，请点击重试...");
            }

            showErrorLayout();

            tvError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoadingLayout();
                    initData();
                }
            });
        }
    }
}