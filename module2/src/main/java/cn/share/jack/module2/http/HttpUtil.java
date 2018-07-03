package cn.share.jack.module2.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import cn.share.jack.module2.http.callback.HttpCallBack;
import cn.share.jack.module2.http.okhttp.OkHttpRequest;

/**
 * Created by jack on 2018/3/6
 */

public class HttpUtil {

    private OkHttpRequest mOkHttpRequest;

    private final int TYPE_POST = 0x0011;
    private final int TYPE_GET = 0x0012;
    private int mType = TYPE_POST;

    private Context mContext;
    private Map<String, Object> mParams;
    private String mUrl;
    private boolean mIsCache;

    public static HttpUtil with(Context context) {
        return new HttpUtil(context);
    }

    public HttpUtil get() {
        mType = TYPE_GET;
        return this;
    }

    public HttpUtil param(String key, Object value) {
        if (!key.isEmpty() && null != value) {
            mParams.put(key, value);
        }
        return this;
    }

    public HttpUtil url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtil isCache(boolean isCache) {
        mIsCache = isCache;
        return this;
    }

    public HttpUtil(Context context) {
        mOkHttpRequest = new OkHttpRequest();
        mParams = new HashMap<>();
        mContext = context;
    }

    public <T> void request() {
        request(null);
    }

    public <T> void request(final HttpCallBack<T> callBack) {
        //TODO 异常判读
        mOkHttpRequest.get(mContext, mUrl, mParams, callBack);
    }
}