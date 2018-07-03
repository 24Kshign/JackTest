package cn.share.jack.module2.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jack on 2018/6/14
 */
public abstract class FRFragment extends Fragment {
    protected abstract int layoutRes();

    /**
     * onViewReallyCreated才是真正创建了View
     */
    protected void onViewReallyCreated(View view) {
    }

    protected boolean isSaveViewStatus() {
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * root view 与 findView() 的区别：
     * isSaveViewStatus()为true的情况下，root view 创建后就一直存在，保存着view的状态
     * findView() 在 Fragment 里控制，通过 FragmentTransaction 进行各种操作的时候会丢失view
     */
    private View mRootView;

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = rootView();
        if (!isSaveViewStatus() || rootView == null) {
            Log.d("TAG", "inflate root view");
            rootView = inflater.inflate(layoutRes(), null);
            mRootView = rootView;
            onViewReallyCreated(rootView);
        } else {
            Log.e("TAG", "root view is already created");
            // 缓存的mRootView需要判断是否已经被加过parent
            // 如果有parent需要从parent删除，要不然会发生这个mRootView已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                Log.e("TAG", "parent != null");
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    /**
     * final了，不能Override了
     */
    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // delegate RzViewController

    public View rootView() {
        return mRootView;
    }

    protected Activity thisActivity() {
        return this.getActivity();
    }

    public <VIEW extends View> VIEW findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }
}
