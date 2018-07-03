package cn.share.jack.module2.http;

/**
 * Created by jack on 2018/3/6
 */

public class SPHttpCache {

    public void saveCache(String finalUrl,String result){
        SPUtil.setStringDefault(finalUrl,result);
    }

    public String getCache(String finalUrl){
        return SPUtil.getStringDefault(finalUrl,"");
    }
}