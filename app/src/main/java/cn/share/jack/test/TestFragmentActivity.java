package cn.share.jack.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import cn.share.jack.test.fragment.FragmentOne;
import cn.share.jack.test.fragment.FragmentTwo;

/**
 * Created by jack on 2018/5/15
 */

public class TestFragmentActivity extends FragmentActivity {

    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        RadioGroup rg = findViewById(R.id.atf_rg_parent);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.atf_rb_one:
                        changeFragment(1);
                        break;
                    case R.id.atf_rb_two:
                        changeFragment(2);
                        break;
                }
            }
        });

        changeFragment(1);
    }

    private void changeFragment(int flag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (null != fragmentOne) {
            fragmentTransaction.hide(fragmentOne);
        }
        if (null != fragmentTwo) {
            fragmentTransaction.hide(fragmentTwo);
        }
        switch (flag) {
            case 1:
                if (null == fragmentOne) {
                    fragmentOne = new FragmentOne();
                    fragmentTransaction.add(R.id.atf_fl_contailer, fragmentOne);
                } else {
                    fragmentTransaction.show(fragmentOne);
                }
                break;
            case 2:
                if (null == fragmentTwo) {
                    fragmentTwo = new FragmentTwo();
                    fragmentTransaction.add(R.id.atf_fl_contailer, fragmentTwo);
                } else {
                    fragmentTransaction.show(fragmentTwo);
                }
                break;
        }
        fragmentTransaction.commit();
    }
}