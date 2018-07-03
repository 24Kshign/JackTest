package cn.share.jack.module2.dialog;

import android.view.View;
import android.view.Window;

/**
 * Created by jack on 2018/1/18
 */

class FRDialogController {

    private FRDialog mFrDialog;
    private Window mWindow;

    private FRDialogViewHelper dialogViewHelper;

    public FRDialogController(FRDialog dialog, Window window) {
        this.mFrDialog = dialog;
        this.mWindow = window;
    }

    //获取dialog
    public FRDialog getDialog() {
        return mFrDialog;
    }

    //获取dialog的window
    public Window getWindow() {
        return mWindow;
    }

    public <T extends View> T getView(int viewId) {
        return dialogViewHelper.getView(viewId);
    }

    public void setDialogViewHelper(FRDialogViewHelper dialogViewHelper) {
        this.dialogViewHelper = dialogViewHelper;
    }

    public void setText(int viewId, CharSequence charSequence) {
        dialogViewHelper.setText(viewId, charSequence);
    }

    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        dialogViewHelper.setOnClickListener(viewId,onClickListener);
    }
}
