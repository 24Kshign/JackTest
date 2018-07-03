package cn.share.jack.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.share.jack.module2.ioc.ViewById;
import cn.share.jack.module2.ioc.ViewUtils;

/**
 * Created by jack on 2018/3/8
 */

public class NumberViewActivity extends Activity {

    private static final String TAG = "tvText";

    @ViewById(R.id.anv_tv_text)
    TextView tvText;
    @ViewById(R.id.anv_btn_click)
    Button btnClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_view);
        ViewUtils.inject(this);

        tvText.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "width1-->" + tvText.getWidth() + "\nheight1-->" + tvText.getHeight());
            }
        });

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                tvText.setText("你好啊");
                tvText.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "width2-->" + tvText.getWidth() + "\nheight2-->" + tvText.getHeight());
                    }
                });
            }
        });
    }
}