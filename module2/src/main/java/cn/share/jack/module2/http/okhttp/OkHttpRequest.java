package cn.share.jack.module2.http.okhttp;

import android.content.Context;

import java.util.Map;

import cn.share.jack.module2.http.SPHttpCache;
import cn.share.jack.module2.http.callback.HttpCallBack;

/**
 * Created by jack on 2018/3/6
 */

public class OkHttpRequest {

    private SPHttpCache mHttpCache;

    public OkHttpRequest() {
        mHttpCache = new SPHttpCache();
    }

    public void get(Context context, String url, Map<String,Object> params, final HttpCallBack callBack){

    }
}