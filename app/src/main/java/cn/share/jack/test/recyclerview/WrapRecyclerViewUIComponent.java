package cn.share.jack.test.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import cn.share.jack.module2.ioc.ViewById;
import cn.share.jack.module2.ioc.ViewUtils;
import cn.share.jack.module2.recyclerview.wrap.WrapRecyclerAdapter;
import cn.share.jack.test.R;
import cn.share.jack.test.network.NetworkErrorUIComponent;

/**
 * Created by jack on 2018/6/11
 */
public class WrapRecyclerViewUIComponent extends RelativeLayout {

    public WrapRecyclerViewUIComponent(@NonNull Context context) {
        super(context);
    }

    public WrapRecyclerViewUIComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerViewUIComponent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @ViewById(R.id.lwr_recyclerview)
    RecyclerView mRecyclerView;
    @ViewById(R.id.lwr_net_work_error)
    NetworkErrorUIComponent mNetworkErrorUIComponent;
    @ViewById(R.id.lwr_fl_network_error)
    FrameLayout mErrorParent;


    //列表数据的Adapter
    private RecyclerView.Adapter mAdapter;
    private WrapRecyclerAdapter mWrapRecyclerAdapter;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewUtils.inject(this);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (null != mAdapter) {
            //取消注册观察者
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }
        mAdapter = adapter;
        if (adapter instanceof WrapRecyclerAdapter) {
            mWrapRecyclerAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mWrapRecyclerAdapter = new WrapRecyclerAdapter(adapter);
        }
        mRecyclerView.setAdapter(mWrapRecyclerAdapter);
        // 注册一个观察者 不然更新没有效果
        mAdapter.registerAdapterDataObserver(mDataObserver);

        // 解决GridLayout添加头部和底部也要占据一行
        mWrapRecyclerAdapter.adjustSpanSize(mRecyclerView);
    }

    // 添加头部
    public void addHeaderView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.addHeaderView(view);
        }
    }

    // 添加底部
    public void addFooterView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.addFooterView(view);
        }
    }

    // 移除头部
    public void removeHeaderView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.removeHeaderView(view);
        }
    }

    // 移除底部
    public void removeFooterView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.removeFooterView(view);
        }
    }

    public void setEmptyView(View emptyView) {
        if (null != emptyView) {
            mErrorParent.addView(emptyView);
            mNetworkErrorUIComponent.setVisibility(View.GONE);
        }
    }

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyDataSetChanged();
            }
            dataChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemRemoved(positionStart);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemChanged(positionStart);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemChanged(positionStart, payload);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemInserted(positionStart);
            }
            dataChanged();
        }
    };

    private void dataChanged() {
        if (mAdapter.getItemCount() == 0) {
            //没有数据
            mRecyclerView.setVisibility(View.GONE);
            mErrorParent.setVisibility(View.VISIBLE);
        } else {
            mErrorParent.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public <T extends RecyclerView.LayoutManager> void setLayoutManager(T layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public <T extends RecyclerView.ItemDecoration> void addItemDecoration(T itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }
}
