package cn.share.jack.test.indicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.share.jack.module2.customtablayout.IndicatorAdapter;
import cn.share.jack.module2.customtablayout.TrackIndicatorView;
import cn.share.jack.module2.ioc.ViewById;
import cn.share.jack.module2.ioc.ViewUtils;
import cn.share.jack.test.R;
import cn.share.jack.test.fragment.FragmentFour;
import cn.share.jack.test.fragment.FragmentOne;
import cn.share.jack.test.fragment.FragmentThree;
import cn.share.jack.test.fragment.FragmentTwo;
import cn.share.jack.test.indicator.uicomponent.TabUIComponent;

/**
 * Created by jack on 2018/4/26
 */

public class IndicatorActivity extends FragmentActivity {

    @ViewById(R.id.ai_indicator)
    TrackIndicatorView trackIndicatorView;
    @ViewById(R.id.ai_viewpager)
    NoScrollViewPager viewPager;

    private String[] titles = {"推荐", "视频", "NBA", "CBA"};

    private int[] images = {R.drawable.tab_loan_market_selector, R.drawable.tab_card_manager_selector
            , R.drawable.tab_cashadvance_selector, R.drawable.tab_personal_selector};

    private Fragment fragmentOne;
    private Fragment fragmentTwo;
    private Fragment fragmentThree;
    private Fragment fragmentFour;

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        ViewUtils.inject(this);

        initFragment();

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });

        trackIndicatorView.setAdapter(new IndicatorAdapter() {
            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TabUIComponent tabUIComponent = (TabUIComponent) LayoutInflater.from(IndicatorActivity.this).inflate(R.layout.layout_tab, parent, false);
                tabUIComponent.setImageView(images[position]);
                tabUIComponent.setTitle(titles[position]);
                return tabUIComponent;
            }

            @Override
            public void highLightIndicator(View view) {
                TabUIComponent tabUIComponent = (TabUIComponent) view;
                tabUIComponent.setTabSelected(true);
            }

            @Override
            public void resetIndicator(View view) {
                TabUIComponent tabUIComponent = (TabUIComponent) view;
                tabUIComponent.setTabSelected(false);
            }
        }, viewPager);

    }

    private void initFragment() {
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        fragmentFour = new FragmentFour();
        mFragmentList.add(fragmentOne);
        mFragmentList.add(fragmentTwo);
        mFragmentList.add(fragmentThree);
        mFragmentList.add(fragmentFour);
    }
}
