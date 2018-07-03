package cn.share.jack.module2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jack on 2018/2/26
 */

public class NetWorkUtil {

    //网络是否可用
    public static boolean isNetWorkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != cm) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (null != networkInfo && networkInfo.isConnected()) {
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}