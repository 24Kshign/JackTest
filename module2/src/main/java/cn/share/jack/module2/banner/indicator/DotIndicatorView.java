package cn.share.jack.module2.banner.indicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jack on 2018/5/8
 */

public class DotIndicatorView extends View {

    private Drawable mDrawable;

    public DotIndicatorView(Context context) {
        super(context);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != mDrawable) {  //传进来的图片不为空
            Bitmap bitmap = getCircleBitmap(drawable2Bitmap(mDrawable));
            //把圆形的bitmap绘制到画布上
            if (null != bitmap) {
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
        }
    }

    /**
     * 获取圆形的bitmap
     *
     * @param bitmap
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        if (null != bitmap) {
            //创建一个空的bitmap
            Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(circleBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setFilterBitmap(true);
            //取圆和Bitmap矩形的交集
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return circleBitmap;
        }
        return null;
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        //创建一个空的bitmap
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}