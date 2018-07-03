package cn.share.jack.module2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import cn.share.jack.module2.R;

/**
 * Created by jack on 2018/1/18
 */

public class FRDialog extends Dialog {

    public FRDialogController controller;

    public FRDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        controller = new FRDialogController(this, getWindow());
    }

    public <T extends View> T getView(int viewId) {
       return controller.getView(viewId);
    }

    public void setText(int viewId, CharSequence charSequence) {
        controller.setText(viewId,charSequence);
    }

    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        controller.setOnClickListener(viewId,onClickListener);
    }

    public static class Builder {

        private FRDialogParams frDialogParams;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            frDialogParams = new FRDialogParams(context, themeResId);
        }

        public Builder setContentView(View view) {
            frDialogParams.mView = view;
            frDialogParams.mViewLayoutRes = 0;
            return this;
        }

        public Builder setContentView(int layoutRes) {
            frDialogParams.mView = null;
            frDialogParams.mViewLayoutRes = layoutRes;
            return this;
        }

        public Builder setCancelable(boolean isCancelable) {
            frDialogParams.mCancelable = isCancelable;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            frDialogParams.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            frDialogParams.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setText(int viewId, CharSequence text) {
            frDialogParams.mTextArray.put(viewId, text);
            return this;
        }

        public Builder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
            frDialogParams.mClickArray.put(viewId, onClickListener);
            return this;
        }

        public Builder setFullWidths() {
            frDialogParams.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder setFromBottom(boolean isAnimation) {
            if (isAnimation) {
                frDialogParams.mAnimations = R.style.dialog_from_bottom_anim;
            }
            frDialogParams.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            frDialogParams.mWidth = width;
            frDialogParams.mHeight = height;
            return this;
        }

        public Builder setDefaultAnimation() {
            frDialogParams.mAnimations = R.style.dialog_scale_anim;
            return this;
        }

        public Builder setAnimation(int styleAnimation) {
            frDialogParams.mAnimations = styleAnimation;
            return this;
        }

        public FRDialog create() {
            FRDialog dialog = new FRDialog(frDialogParams.mContext, frDialogParams.mThemeResId);
            frDialogParams.apply(dialog.controller);
            dialog.setCancelable(frDialogParams.mCancelable);
            if (!frDialogParams.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            if (null != frDialogParams.mOnCancelListener) {
                dialog.setOnCancelListener(frDialogParams.mOnCancelListener);
            }
            if (null != frDialogParams.mOnKeyListener) {
                dialog.setOnKeyListener(frDialogParams.mOnKeyListener);
            }
            return dialog;
        }

        public FRDialog show() {
            final FRDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
