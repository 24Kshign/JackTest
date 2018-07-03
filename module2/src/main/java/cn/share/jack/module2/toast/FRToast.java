package cn.share.jack.module2.toast;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.share.jack.module2.CygApplication;
import cn.share.jack.module2.R;

/**
 * Created by jack on 2018/4/22
 */

public class FRToast {

    private int mLayoutRes;
    private View mView;
    private String mMessage;
    private boolean isDefaultView;
    private Toast mToast;
    private TextView mTvToast;

    private static FRToast mFRToast;

    private FRToast() {

    }

    public static FRToast with() {
        if (null == mFRToast) {
            mFRToast = new FRToast();
        }
        return mFRToast;
    }

    /**
     * 判断当前的线程是不是在主线程
     *
     * @return
     */
    public boolean isRunInMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public FRToast toastView(int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    public FRToast defaultToastView() {
        isDefaultView = true;
        mLayoutRes = R.layout.layout_toast;
        return this;
    }

    public FRToast toastView(View view) {
        mView = view;
        return this;
    }

    public FRToast setMessage(String message) {
        mMessage = message;
        return this;
    }

    private void showToastSafe(final String str) {
        if (isRunInMainThread()) {
            showToast(str);
        } else {
            CygApplication.getInstance().getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    @SuppressLint("ShowToast")
    private void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(CygApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void show() {
        if (null == mView && mLayoutRes == 0) {
            showToastSafe(mMessage);
        } else {
            if (null == mToast) {
                mToast = new Toast(CygApplication.getInstance());
            }
            if (mView == null) {
                if (mLayoutRes != 0) {
                    mView = LayoutInflater.from(CygApplication.getInstance()).inflate(mLayoutRes, null);
                }
            }
            if (isDefaultView) {
                if (null == mTvToast) {
                    mTvToast = mView.findViewById(R.id.lt_tv_toast);
                }
                mTvToast.setText(mMessage);
            }
            if (null == mToast.getView()) {
                mToast.setView(mView);
            }
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }
}