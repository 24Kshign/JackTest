package cn.share.jack.test.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.share.jack.module2.titlebar.FRTitleBar;
import cn.share.jack.test.R;
import cn.share.jack.test.base.BaseFragment;

/**
 * Created by jack on 2018/5/15
 */

public class FragmentThree extends BaseFragment {

    private TextView tvText;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_three;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        super.onViewReallyCreated(view);
        tvText = findViewById(R.id.fth_tv_text);
    }

    @Override
    protected boolean initTitleBar() {
        new FRTitleBar.FRTiTitleBarBuilder(thisActivity(), findViewById(R.id.fth_rl_main))
                .setBackgroundColor(R.color.colorPrimary)
                .setTitleContent("第三个")
                .setRightContent("右边")
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(thisActivity(), "点击了右边", Toast.LENGTH_SHORT).show();
                    }
                })
                .builder();
        return true;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    thisActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showContentView();
                            tvText.setText("第三个页面");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}