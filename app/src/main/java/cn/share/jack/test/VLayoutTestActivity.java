package cn.share.jack.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;

import java.util.ArrayList;
import java.util.List;

import cn.share.jack.module2.toast.FRToast;
import cn.share.jack.module2.vlayout.BaseVLayoutAdapter;
import cn.share.jack.test.adapter.VLayoutGridAdapter;
import cn.share.jack.test.adapter.VLayoutListAdapter;
import cn.share.jack.test.adapter.VLayoutOnePlusNAdapter;
import cn.share.jack.test.bean.ListBean;
import cn.share.jack.test.widget.VLayoutRecyclerViewUIComponent;

/**
 * Created by jack on 2018/4/13
 */

public class VLayoutTestActivity extends Activity {

    private VLayoutRecyclerViewUIComponent recyclerView;
    private VLayoutListAdapter adapter;
    private VLayoutOnePlusNAdapter onePlusNAdapter;
    private VLayoutGridAdapter gridAdapter;

    private View viewHeader;
    private View viewFooter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout_test);
        recyclerView = findViewById(R.id.avt_recyclerview);

        adapter = new VLayoutListAdapter(this);

        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(10);
        linearLayoutHelper.setMarginBottom(50);
        adapter.setLayoutHelper(linearLayoutHelper);
        viewHeader = LayoutInflater.from(this).inflate(R.layout.layout_header_view, null);
        viewFooter = LayoutInflater.from(this).inflate(R.layout.layout_footer_view, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 260);
        viewHeader.setLayoutParams(params);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 130);
        viewFooter.setLayoutParams(params);
        adapter.addHeader(viewHeader);
        adapter.addFooter(viewFooter);
        recyclerView.addAdapter(adapter);

        onePlusNAdapter = new VLayoutOnePlusNAdapter(this);
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper(4);
        onePlusNLayoutHelper.setBgColor(Color.WHITE);
        onePlusNLayoutHelper.setColWeights(new float[]{38f});  //左边一栏宽度占38%
        onePlusNLayoutHelper.setRowWeight(55f);  //右边上面一栏高度占55%
        onePlusNLayoutHelper.setMarginBottom(50);
        onePlusNAdapter.setLayoutHelper(onePlusNLayoutHelper);
        recyclerView.addAdapter(onePlusNAdapter);

        gridAdapter = new VLayoutGridAdapter(this);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setBgColor(Color.WHITE);
        gridAdapter.setLayoutHelper(gridLayoutHelper);
        gridAdapter.addHeader(viewHeader);
        gridAdapter.addFooter(viewFooter);
        recyclerView.addAdapter(gridAdapter);
        initData();

        adapter.setOnItemClickListener(new BaseVLayoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FRToast.with().defaultToastView().setMessage("点击了第" + (position + 1) + "个item").show();
//                Toast.makeText(VLayoutTestActivity.this, "点击了第" + (position + 1) + "个item", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemLongClickListener(new BaseVLayoutAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                FRToast.with().setMessage("长按了第" + (position + 1) + "个item").show();
            }
        });
    }

    private void initData() {
        List<ListBean> mDataList = new ArrayList<>();
        ListBean listBean;
        for (int i = 0; i < 8; i++) {
            listBean = new ListBean(R.mipmap.ic_launcher_round, "Name" + (i + 1));
            mDataList.add(listBean);
        }
        adapter.setDataList(mDataList);

        List<String> imageList = new ArrayList<>();
        imageList.add("http://file.17gwx.com/sqkb/element/2018/04/08/806925ac988a9b118b.jpg");
        imageList.add("http://file.17gwx.com/sqkb/element/2018/04/08/592375ac9df48c0d7e.jpg");
        imageList.add("http://file.17gwx.com/sqkb/element/2018/04/08/935985ac986197d37f.jpeg");
        imageList.add("http://file.17gwx.com/sqkb/element/2017/12/25/873915a40f33db8d55.jpg");
        onePlusNAdapter.setDataList(imageList);

        gridAdapter.setDataList(mDataList);
    }
}