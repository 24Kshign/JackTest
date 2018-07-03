package cn.share.jack.test.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.share.jack.module2.vlayout.BaseViewHolder;
import cn.share.jack.test.R;
import cn.share.jack.test.bean.ListBean;

/**
 * Created by jack on 2018/4/13
 */

public class VLayoutListViewHolder extends BaseViewHolder<ListBean> {

    private ImageView ivImage;
    private TextView tvName;

    public VLayoutListViewHolder(View itemView) {
        super(itemView);
        ivImage = findViewById(R.id.il_iv_image);
        tvName = findViewById(R.id.il_tv_name);
    }

    @Override
    protected void onItemDataUpdated(ListBean listBean) {
        ivImage.setImageResource(listBean.getImageRes());
        tvName.setText(listBean.getName());
    }
}