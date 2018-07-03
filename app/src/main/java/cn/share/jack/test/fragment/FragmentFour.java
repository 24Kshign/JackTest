package cn.share.jack.test.fragment;

import android.view.View;
import android.widget.TextView;

import cn.share.jack.test.R;
import cn.share.jack.test.base.BaseFragment;

/**
 * Created by jack on 2018/5/15
 */

public class FragmentFour extends BaseFragment {

    private TextView tvText;

    @Override
    protected int layoutRes() {
        return R.layout.fragment_four;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        super.onViewReallyCreated(view);
        tvText = findViewById(R.id.ff_tv_text);
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
                            tvText.setText("第四个页面");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}