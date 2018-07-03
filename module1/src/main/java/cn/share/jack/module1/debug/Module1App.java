package cn.share.jack.module1.debug;

import android.app.Application;

import cn.share.jack.module2.AppContext;

/**
 * Created by jack on 2018/1/12
 */

public class Module1App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initARoute(this);
    }
}
