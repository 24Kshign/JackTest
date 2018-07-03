package cn.share.jack.test.util;

import android.widget.Toast;

import cn.share.jack.test.App;

/**
 * Created by jack on 2018/5/23
 */

public class ToastUtil {

    private static String oldMsg;
    private static Toast mToast = null;
    private static long lastTime = 0;
    private static long nextTime = 0;

    public static void showToast(String s) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getInstance(), s, Toast.LENGTH_SHORT);
            mToast.show();
            lastTime = System.currentTimeMillis();
        } else {
            nextTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (nextTime - lastTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                oldMsg = s;
                mToast.setText(s);
                mToast.show();
            }
        }
        lastTime = nextTime;
    }
}