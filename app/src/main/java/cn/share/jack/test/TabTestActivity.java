package cn.share.jack.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by jack on 2018/3/31
 */

public class TabTestActivity extends Activity {

    private TabLayout tab_tagContainer;
    private ScrollChangedScrollView sv_bodyContainer;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_7;
    private TextView tv_8;
    private TextView tv_9;
    // 头部导航标签
    private String[] navigationTag = {"图片", "啤酒", "饮料", "矿泉水", "瓜子", "花生", "八宝粥", "泡面", "鸡爪", "火腿肠"};
    /**
     * 是否是ScrollView主动动作
     * false:是ScrollView主动动作
     * true:是TabLayout 主动动作
     */
    private boolean tagFlag = false;
    /**
     * 用于切换内容模块，相应的改变导航标签，表示当一个所处的位置
     */
    private int lastTagIndex = 0;
    /**
     * 用于在同一个内容模块内滑动，锁定导航标签，防止重复刷新标签
     * true: 锁定
     * false ; 没有锁定
     */
    private boolean content2NavigateFlagInnerLock = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);
        tab_tagContainer = (TabLayout) findViewById(R.id.anchor_tagContainer);
        sv_bodyContainer = (ScrollChangedScrollView) findViewById(R.id.anchor_bodyContainer);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_7 = (TextView) findViewById(R.id.tv_7);
        tv_8 = (TextView) findViewById(R.id.tv_8);
        tv_9 = (TextView) findViewById(R.id.tv_9);

        tv_1.setText(navigationTag[1]);
        tv_2.setText(navigationTag[2]);
        tv_3.setText(navigationTag[3]);
        tv_4.setText(navigationTag[4]);
        tv_5.setText(navigationTag[5]);
        tv_6.setText(navigationTag[6]);
        tv_7.setText(navigationTag[7]);
        tv_8.setText(navigationTag[8]);
        tv_9.setText(navigationTag[9]);

        // 添加页内导航标签
        for (String item : navigationTag) {
            tab_tagContainer.addTab(tab_tagContainer.newTab().setText(item));
        }

        initListener();
    }

    private void initListener() {
        sv_bodyContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //表明当前的动作是由 ScrollView 触发和主导
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    tagFlag = true;
                }
                return false;
            }
        });
        sv_bodyContainer.setScrollViewListener(new ScrollChangedScrollView.ScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
                scrollRefreshNavigationTag(scrollView);
            }

            @Override
            public void onScrollStop(boolean isStop) {
            }
        });
        tab_tagContainer.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //表明当前的动作是由 TabLayout 触发和主导
                tagFlag = false;
                // 根据点击的位置，使ScrollView 滑动到对应区域
                int position = tab.getPosition();
                // 计算点击的导航标签所对应内容区域的高度
                int targetY = 0;
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        targetY = tv_1.getTop();
                        break;
                    case 2:
                        targetY = tv_2.getTop();
                        break;
                    case 3:
                        targetY = tv_3.getTop();
                        break;
                    case 4:
                        targetY = tv_4.getTop();
                        break;
                    case 5:
                        targetY = tv_5.getTop();
                        break;
                    case 6:
                        targetY = tv_6.getTop();
                        break;
                    case 7:
                        targetY = tv_7.getTop();
                        break;
                    case 8:
                        targetY = tv_8.getTop();
                        break;
                    case 9:
                        targetY = tv_9.getTop();
                        break;
                    default:
                        break;
                }
                // 移动到对应的内容区域
                sv_bodyContainer.smoothScrollTo(0, targetY + 5);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    /**
     * 内容区域滑动刷新导航标签
     *
     * @param scrollView 内容模块容器
     */
    private void scrollRefreshNavigationTag(ScrollView scrollView) {
        if (scrollView == null) {
            return;
        }
        // 获得ScrollView滑动距离
        int scrollY = scrollView.getScrollY();
        // 确定ScrollView当前展示的顶部内容属于哪个内容模块
        if (scrollY > tv_9.getTop()) {
            refreshContent2NavigationFlag(9);

        } else if (scrollY > tv_8.getTop()) {
            refreshContent2NavigationFlag(8);

        } else if (scrollY > tv_7.getTop()) {
            refreshContent2NavigationFlag(7);

        } else if (scrollY > tv_6.getTop()) {
            refreshContent2NavigationFlag(6);

        } else if (scrollY > tv_5.getTop()) {
            refreshContent2NavigationFlag(5);

        } else if (scrollY > tv_4.getTop()) {
            refreshContent2NavigationFlag(4);

        } else if (scrollY > tv_3.getTop()) {
            refreshContent2NavigationFlag(3);

        } else if (scrollY > tv_2.getTop()) {
            refreshContent2NavigationFlag(2);

        } else if (scrollY > tv_1.getTop()) {
            refreshContent2NavigationFlag(1);

        } else {
            refreshContent2NavigationFlag(0);
        }
    }

    /**
     * 刷新标签
     *
     * @param currentTagIndex 当前模块位置
     */
    private void refreshContent2NavigationFlag(int currentTagIndex) {
        // 上一个位置与当前位置不一致是，解锁内部锁，是导航可以发生变化
        if (lastTagIndex != currentTagIndex) {
            content2NavigateFlagInnerLock = false;
        }
        if (!content2NavigateFlagInnerLock) {
            // 锁定内部锁
            content2NavigateFlagInnerLock = true;
            // 动作是由ScrollView触发主导的情况下，导航标签才可以滚动选中
            if (tagFlag) {
                tab_tagContainer.setScrollPosition(currentTagIndex, 0, true);
            }
        }
        lastTagIndex = currentTagIndex;
    }
}
