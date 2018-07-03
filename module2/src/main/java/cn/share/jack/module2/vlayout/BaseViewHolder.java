package cn.share.jack.module2.vlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by jack on 2018/4/13
 */

public class BaseViewHolder<DATA> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewSparseArray;
    private int mPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViewSparseArray = new SparseArray<>();
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T findViewById(int viewId) {
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    protected Context getContext() {
        return itemView.getContext();
    }

    protected void onItemDataUpdated(DATA data) {

    }

    public int getRealPosition() {
        return mPosition;
    }

    public final void setData(DATA data, int position) {
        mPosition = position;
        if (null != data) {
            try {
                onItemDataUpdated(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
