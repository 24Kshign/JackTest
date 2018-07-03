package cn.share.jack.module2;

import android.os.Handler;
import android.support.multidex.MultiDexApplication;

/**
 * Created by jack on 2018/3/6
 */

public class CygApplication extends MultiDexApplication {

    public Handler mMainThreadHandler;

    private static CygApplication sInstance;

    public static CygApplication getInstance() {
        return sInstance;
    }

    protected CygApplication() {
        sInstance = this;
    }

    public Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMainThreadHandler = new Handler();
    }
}
