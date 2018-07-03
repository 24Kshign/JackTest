package cn.share.jack.module2.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by jack on 2018/2/26
 * findViewById的辅助类
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        mActivity = activity;
    }

    public ViewFinder(View view) {
        mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity == null ? mView.findViewById(viewId) : mActivity.findViewById(viewId);
    }
}