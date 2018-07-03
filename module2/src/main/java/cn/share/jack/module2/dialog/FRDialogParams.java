package cn.share.jack.module2.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by jack on 2018/1/18
 */

class FRDialogParams {

    public Context mContext;
    public int mThemeResId;
    //点击空白处是否取消
    public boolean mCancelable = true;
    //cancel点击事件
    public DialogInterface.OnCancelListener mOnCancelListener;
    //key点击事件
    public DialogInterface.OnKeyListener mOnKeyListener;
    //dialog的view
    public View mView;
    //dialog的资源id
    public int mViewLayoutRes;
    //存放文字
    public SparseArray<CharSequence> mTextArray = new SparseArray<>();
    //存放点击事件
    public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
    public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    //位置
    public int mGravity = Gravity.CENTER;
    //动画
    public int mAnimations;
    public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    public FRDialogParams(Context mContext, int mThemeResId) {
        this.mContext = mContext;
        this.mThemeResId = mThemeResId;
    }

    //绑定和设置参数
    public void apply(FRDialogController controller) {
        //设置布局
        FRDialogViewHelper viewHelper = null;
        if (mViewLayoutRes != 0) {
            viewHelper = new FRDialogViewHelper(mContext, mViewLayoutRes);
        }
        if (null != mView) {
            viewHelper = new FRDialogViewHelper();
            viewHelper.setContentView(mView);
        }
        if (null == viewHelper) {
            throw new IllegalArgumentException("请设置布局ContentView");
        }

        //给dialog设置布局
        controller.getDialog().setContentView(viewHelper.getContentView());

        controller.setDialogViewHelper(viewHelper);

        //设置文本
        int textArraySize = mTextArray.size();
        for (int i = 0; i < textArraySize; i++) {
            viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
        }

        //设置点击事件
        int clickArraySize = mClickArray.size();
        for (int i = 0; i < clickArraySize; i++) {
            viewHelper.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
        }

        //配置自定义效果
        Window window = controller.getWindow();
        window.setGravity(mGravity);
        if (mAnimations != 0) {
            window.setWindowAnimations(mAnimations);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = mWidth;
        lp.height = mHeight;
        window.setAttributes(lp);
    }
}
