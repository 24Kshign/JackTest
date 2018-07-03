package cn.share.jack.module2.http.callback;

/**
 * Created by jack on 2018/3/6
 */

public interface HttpCallBack<T> {
    void onSuccess(T data);

    void onFailure(String message);
}