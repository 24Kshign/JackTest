package cn.share.jack.test.network;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.share.jack.module2.ioc.ViewById;
import cn.share.jack.module2.ioc.ViewUtils;
import cn.share.jack.test.R;

/**
 * Created by jack on 2018/6/11
 */
public class NetworkErrorUIComponent extends LinearLayout {
    public NetworkErrorUIComponent(Context context) {
        super(context);
    }

    public NetworkErrorUIComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NetworkErrorUIComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @ViewById(R.id.lne_tv_refresh)
    TextView tvRefresh;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewUtils.inject(this);
    }

    public void setOnRefreshClickListener(OnClickListener onClickListener) {
        tvRefresh.setOnClickListener(onClickListener);
    }
}