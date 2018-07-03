package cn.share.jack.module2.vlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2018/4/13
 */

public abstract class BaseVLayoutAdapter<DATA, VH extends BaseViewHolder<DATA>> extends DelegateAdapter.Adapter<VH> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;

    private Context mContext;
    private List<DATA> mDataList;
    //布局管理器
    private LayoutHelper mLayoutHelper;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;

    private View mHeaderView;
    private View mFooterView;
    private boolean isHaveHeader;
    private boolean isHaveFooter;

    public BaseVLayoutAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public void onBindViewHolder(final VH holder, @SuppressLint("RecyclerView") final int position) {
        final int pos;
        if (isHaveHeader && isHaveFooter) {
            if (position == 0 || position == mDataList.size() + 1) {
                return;
            }
            pos = position - 1;
        } else if (isHaveHeader && position != 0) {
            pos = position - 1;
        } else if (!isHaveHeader && !isHaveFooter && position == mDataList.size()) {
            pos = position;
        } else {
            pos = position;
        }
        holder.setData(mDataList.get(pos), pos);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(pos);
                }
            });
        }
        if (null != onItemLongClickListener) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(pos);
                    return true;
                }
            });
        }

        if (mLayoutHelper instanceof GridLayoutHelper) {
            ((GridLayoutHelper) mLayoutHelper).setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHaveHeader && position - getStartPosition() == 0) {
                        return ((GridLayoutHelper) mLayoutHelper).getSpanCount();
                    } else if (isHaveHeader && isHaveFooter && position - getStartPosition() == mDataList.size() + 1) {
                        return ((GridLayoutHelper) mLayoutHelper).getSpanCount();
                    } else if (!isHaveHeader && isHaveFooter && position - getStartPosition() == mDataList.size()) {
                        return ((GridLayoutHelper) mLayoutHelper).getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return (VH) new BaseViewHolder<>(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            return (VH) new BaseViewHolder<>(mFooterView);
        }
        return onCreateNormalView(parent, viewType);
    }

    protected abstract VH onCreateNormalView(ViewGroup parent, int viewType);

    public BaseVLayoutAdapter setLayoutHelper(LayoutHelper layoutHelper) {
        this.mLayoutHelper = layoutHelper;
        return this;
    }

    public BaseVLayoutAdapter addHeader(View viewHeader) {
        mHeaderView = viewHeader;
        isHaveHeader = true;
        notifyDataSetChanged();
        return this;
    }

    public BaseVLayoutAdapter addFooter(View viewFooter) {
        mFooterView = viewFooter;
        isHaveFooter = true;
        notifyDataSetChanged();
        return this;
    }

    public BaseVLayoutAdapter setDataList(List<DATA> dataList) {
        setDataList(dataList, true);
        return this;
    }

    public BaseVLayoutAdapter setLoadMoreDataList(List<DATA> dataList) {
        mDataList.addAll(dataList);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 填充数据,此方法会清空以前的数据
     *
     * @param dataList 需要显示的数据
     */
    public void setDataList(List<DATA> dataList, boolean notifyDataSetChanged) {
        if (mDataList != null) {
            mDataList.clear();
        }
        if (dataList != null) {
            if (mDataList == null) {
                mDataList = new ArrayList<>();
            }
            mDataList.addAll(dataList);
        }
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void removeAllDataList() {
        if (mDataList != null) {
            mDataList.clear();
            notifyDataSetChanged();
        }
    }

    public Context getContext() {
        return mContext;
    }

    public List<DATA> getDataList() {
        return mDataList;
    }

    public DATA getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHaveHeader && position == 0) {
            return TYPE_HEADER;
        } else if (isHaveHeader && isHaveFooter && position == mDataList.size() + 1) {
            return TYPE_FOOTER;
        } else if (!isHaveHeader && isHaveFooter && position == mDataList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (null == mDataList) {
            return 0;
        }
        int size = mDataList.size();
        if (isHaveHeader) {
            size++;
        }
        if (isHaveFooter) {
            size++;
        }
        return size;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }
}