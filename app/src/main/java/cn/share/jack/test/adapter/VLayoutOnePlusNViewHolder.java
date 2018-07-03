package cn.share.jack.test.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.share.jack.module2.vlayout.BaseViewHolder;
import cn.share.jack.test.R;

/**
 * Created by jack on 2018/4/19
 */

public class VLayoutOnePlusNViewHolder extends BaseViewHolder<String> {

    private ImageView ivImage;
    private View viewLineTop;
    private View viewLineRight;
    private View viewLineBottom;

    public VLayoutOnePlusNViewHolder(View itemView) {
        super(itemView);
        ivImage = findViewById(R.id.iop_iv_image);
        viewLineTop = findViewById(R.id.iop_view_line_top);
        viewLineRight = findViewById(R.id.iop_view_line_right);
        viewLineBottom = findViewById(R.id.iop_view_line_bottom);
    }

    @Override
    protected void onItemDataUpdated(String s) {
        Glide.with(getContext()).load(s).into(ivImage);
        if (getRealPosition() == 0) {
            viewLineTop.setVisibility(View.VISIBLE);
            viewLineRight.setVisibility(View.VISIBLE);
        } else if (getRealPosition() == 1) {
            viewLineTop.setVisibility(View.VISIBLE);
            viewLineBottom.setVisibility(View.VISIBLE);
        } else if (getRealPosition() == 2) {
            viewLineRight.setVisibility(View.VISIBLE);
        }
    }
}