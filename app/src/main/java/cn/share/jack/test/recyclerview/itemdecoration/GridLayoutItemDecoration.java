package cn.share.jack.test.recyclerview.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jack on 2018/5/16
 */

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;

    public GridLayoutItemDecoration(Context context, int drawableRes) {
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
            //留出分割线，右边和下边（最右边和最下边都不需要留）
            int bottom = mDrawable.getIntrinsicHeight();
            int right = mDrawable.getIntrinsicWidth();
            if (isLastColumn(view, parent)) {
                right = 0;
            }
            if (isLastRow(view, parent)) {
                bottom = 0;
            }
            outRect.bottom = bottom;
            outRect.right = right;
        }
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            //获取列数
            return gridLayoutManager.getSpanCount();
        }
        return 1;
    }

    //最后一列
    private boolean isLastColumn(View view, RecyclerView parent) {
        //获取当前位置
        int position = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        return (position + 1) % spanCount == 0;
    }

    //最后一行
    private boolean isLastRow(View view, RecyclerView parent) {
        //当前位置
        int position = parent.getChildAdapterPosition(view);
        //获取列数
        int spanCount = getSpanCount(parent);
        //总的条目数
        int childCount = parent.getAdapter().getItemCount();
        //获取行数
        int rowCount = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
        //最后一行：当前位置 > (行数-1)*列数
        return (position + 1) > ((rowCount - 1) * spanCount);
    }

    /**
     * 绘制分割线
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (null != mDrawable) {
            drawHorizontal(canvas, parent);
            drawVertical(canvas, parent);
        }
    }

    /**
     * 绘制水平方向
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - params.leftMargin;
            int right = childView.getRight() + mDrawable.getIntrinsicWidth() + params.rightMargin;
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            //计算水平分割线的位置
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
    }

    /**
     * 绘制竖直方向
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getTop() - params.topMargin;
            int bottom = childView.getBottom() + params.bottomMargin;
            int left = childView.getRight() + params.rightMargin;
            int right = left + mDrawable.getIntrinsicWidth();
            //计算水平分割线的位置
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(canvas);
        }
    }
}