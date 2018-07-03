package cn.share.jack.test.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import cn.share.jack.test.R;

/**
 * Created by jack on 2018/4/13
 */

public class VLayoutRecyclerViewUIComponent extends FrameLayout {

    public VLayoutRecyclerViewUIComponent(@NonNull Context context) {
        super(context);
    }

    public VLayoutRecyclerViewUIComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VLayoutRecyclerViewUIComponent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private RecyclerView mRecyclerView;
    private VirtualLayoutManager layoutManager;
    private DelegateAdapter mAdapter;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = findViewById(R.id.lvr_recyclerview);


        layoutManager = new VirtualLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);
        mAdapter = new DelegateAdapter(layoutManager, false);
        mRecyclerView.setAdapter(mAdapter);
    }

    public <T extends DelegateAdapter.Adapter> void addAdapter(T adapter) {
        mAdapter.addAdapter(adapter);
    }
}