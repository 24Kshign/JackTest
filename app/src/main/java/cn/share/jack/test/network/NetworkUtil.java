package cn.share.jack.test.network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import cn.share.jack.test.R;

/**
 * Created by jack on 2018/6/11
 */
public class NetworkUtil {

    public static View getNetworkErrorView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.layout_empty, null);
    }
}