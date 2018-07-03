package cn.share.jack.test;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import cn.share.jack.test.base.BaseActivity;
import cn.share.jack.test.recyclerview.RecyclerViewActivity;

/**
 * Created by jack on 2018/1/12
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void initView() {
        super.initView();


        findViewById(R.id.al_btn_to_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RecyclerViewActivity.class));
            }
        });

        findViewById(R.id.al_btn_to_indicator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, TestActivity.class));
            }
        });


    }

    @Override
    protected ViewGroup getRootLayout() {
        return findViewById(R.id.al_ll_main);
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean needShowLoadingView() {
        return false;
    }
}
