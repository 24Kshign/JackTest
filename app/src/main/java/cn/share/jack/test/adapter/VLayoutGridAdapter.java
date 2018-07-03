package cn.share.jack.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.share.jack.module2.vlayout.BaseVLayoutAdapter;
import cn.share.jack.test.R;
import cn.share.jack.test.bean.ListBean;

/**
 * Created by jack on 2018/4/19
 */

public class VLayoutGridAdapter extends BaseVLayoutAdapter<ListBean,VLayoutListViewHolder> {

    public VLayoutGridAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected VLayoutListViewHolder onCreateNormalView(ViewGroup parent, int viewType) {
        return new VLayoutListViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_grid, parent, false));
    }
}
