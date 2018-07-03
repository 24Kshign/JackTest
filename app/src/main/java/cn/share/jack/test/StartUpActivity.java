package cn.share.jack.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by jack on 2018/1/31
 */

public class StartUpActivity extends Activity {

    private MyHandler mMyHandler = new MyHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delayStartMainActivity();
    }

    private void delayStartMainActivity() {
        mMyHandler.sendEmptyMessageDelayed(1, 1500);
    }

    private static class MyHandler extends Handler {
        WeakReference<StartUpActivity> mWeakReference;

        MyHandler(StartUpActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StartUpActivity activity = mWeakReference.get();
            switch (msg.what) {
                case 1:
                    if (null != activity) {
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                        activity.mMyHandler.removeCallbacksAndMessages(null);
                        activity.finish();
                    }
                    break;
            }
        }
    }
}
