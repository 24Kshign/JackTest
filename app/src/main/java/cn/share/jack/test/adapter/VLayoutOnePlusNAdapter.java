package cn.share.jack.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cn.share.jack.module2.vlayout.BaseVLayoutAdapter;
import cn.share.jack.test.R;

/**
 * Created by jack on 2018/4/19
 */

public class VLayoutOnePlusNAdapter extends BaseVLayoutAdapter<String, VLayoutOnePlusNViewHolder> {

    public VLayoutOnePlusNAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected VLayoutOnePlusNViewHolder onCreateNormalView(ViewGroup parent, int viewType) {
        return new VLayoutOnePlusNViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_one_plus, parent, false));
    }
}