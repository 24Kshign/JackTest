package cn.share.jack.test.indicator.uicomponent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.share.jack.test.R;

/**
 * Created by jack on 2018/5/30
 */
public class TabUIComponent extends RelativeLayout {
    public TabUIComponent(Context context) {
        super(context);
    }

    public TabUIComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabUIComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ImageView ivImage;
    private TextView tvTitle;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivImage = findViewById(R.id.lt_iv_imageView);
        tvTitle = findViewById(R.id.lt_tv_title);
    }

    public void setTabSelected(boolean isSelected) {
        ivImage.setSelected(isSelected);
        tvTitle.setSelected(isSelected);
    }

    public void setImageView(int res) {
        ivImage.setImageResource(res);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
