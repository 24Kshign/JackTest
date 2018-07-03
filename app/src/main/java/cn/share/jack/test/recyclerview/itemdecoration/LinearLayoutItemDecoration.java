package cn.share.jack.test.recyclerview.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jack on 2018/5/16
 */

public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;

    private int mPosition;

    public LinearLayoutItemDecoration(Context context, int drawableRes) {
        //获取drawable
        if (drawableRes != 0) {
            mDrawable = ContextCompat.getDrawable(context, drawableRes);
        }
    }

    /**
     * 留出分割线位置
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (null != mDrawable) {
            int position = parent.getChildAdapterPosition(view);

            //在每个底部的位置留出10px来绘制分割线，但是最后一个位置不需要分割线
//            int viewType = parent.getAdapter().getItemViewType(position);
//
//            if (viewType >= BASE_ITEM_TYPE_HEADER - 1 && viewType < BASE_ITEM_TYPE_FOOTER) {
//                mPosition = position;
//                Log.e("TAG", "mPosition---->" + mPosition);
//                return;
//            }
            if (position != 0 && mPosition + 1 != position) {
                outRect.top = mDrawable.getIntrinsicHeight();
            }
        }
    }

    /**
     * 绘制分割线
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (null != mDrawable) {
            //利用Canvas绘制
            //在每一个item头部绘制
            int childCount = parent.getChildCount();
            Rect rect = new Rect();
            rect.left = parent.getPaddingLeft();
            rect.right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 1; i < childCount; i++) {
                //分割线的底部是Item的头部
//                if (mPosition + 1 == i) {
//                    continue;
//                }
                rect.bottom = parent.getChildAt(i).getTop();
                rect.top = rect.bottom - mDrawable.getIntrinsicHeight();
                mDrawable.setBounds(rect);
                mDrawable.draw(canvas);
            }
        }
    }
}