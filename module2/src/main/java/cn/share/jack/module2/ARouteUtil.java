package cn.share.jack.module2;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by jack on 2018/1/16
 */

public class ARouteUtil {

    private ARouteUtil() {

    }


    public static void start(String route, Bundle bundle) {
//        ARouter.getInstance().build(route).with(bundle).navigation();
    }

    public static void start(String route) {
        start(route, null);
    }

//    public static void start(Context context, String route, NavigationCallback navigationCallback){
//        ARouter.getInstance().build(route).navigation(context,navigationCallback);
//    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void startForResult(Activity activity, String route, Bundle bundle, int requestCode) {
//        ARouter.getInstance().build(route).with(bundle).navigation(activity, requestCode);
    }

    public static void startForResult(Activity activity, String route, int requestCode) {
//        startForResult(activity, route, null, requestCode);
    }
}