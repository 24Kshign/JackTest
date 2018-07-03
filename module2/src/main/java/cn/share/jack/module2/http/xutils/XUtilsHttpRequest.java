package cn.share.jack.module2.http.xutils;

import android.content.Context;

import java.util.Map;

import cn.share.jack.module2.http.SPHttpCache;
import cn.share.jack.module2.http.callback.HttpCallBack;

/**
 * Created by jack on 2018/3/6
 */

public class XUtilsHttpRequest {

    private SPHttpCache mHttpCache;

    public XUtilsHttpRequest() {
        mHttpCache = new SPHttpCache();
    }

    public void get(Context context, String url, Map<String,Object> params, final HttpCallBack callBack){

    }
}