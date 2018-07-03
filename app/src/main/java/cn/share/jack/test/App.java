package cn.share.jack.test;

import cn.share.jack.module2.AppContext;
import cn.share.jack.module2.CygApplication;

/**
 * Created by jack on 2018/1/12
 */

public class App extends CygApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initARoute(this);
    }
}
