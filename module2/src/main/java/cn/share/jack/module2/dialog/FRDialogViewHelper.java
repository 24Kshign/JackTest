package cn.share.jack.module2.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by jack on 2018/1/18
 */

class FRDialogViewHelper {

    private View mContentView;

    private SparseArray<WeakReference<View>> mViews;

    public FRDialogViewHelper(Context context, int viewLayoutRes) {
        this();
        mContentView = LayoutInflater.from(context).inflate(viewLayoutRes, null);
    }

    public FRDialogViewHelper() {
        mViews = new SparseArray<>();
    }

    public void setContentView(View view) {
        this.mContentView = view;
    }

    public View getContentView() {
        return mContentView;
    }

    /**
     * 设置控件文本
     *
     * @param viewId
     * @param charSequence
     */
    public void setText(int viewId, CharSequence charSequence) {
        TextView tv = getView(viewId);
        if (null != tv) {
            tv.setText(charSequence);
        }
    }

    public <T extends View> T getView(int viewId) {
        //减少findViewById的次数
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if (null != viewWeakReference) {
            view = mViews.get(viewId).get();
        }
        if (null == view) {
            view = mContentView.findViewById(viewId);
            if (null != view) {
                mViews.put(viewId, new WeakReference<View>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置控件点击事件
     *
     * @param viewId
     * @param onClickListener
     */
    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        if (null != view) {
            view.setOnClickListener(onClickListener);
        }
    }
}
