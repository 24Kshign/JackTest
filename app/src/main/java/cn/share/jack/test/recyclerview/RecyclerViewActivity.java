package cn.share.jack.test.recyclerview;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.share.jack.module2.ioc.ViewById;
import cn.share.jack.module2.ioc.ViewUtils;
import cn.share.jack.module2.recyclerview.RecyclerOnItemClickListener;
import cn.share.jack.module2.recyclerview.RecyclerViewCommonAdapter;
import cn.share.jack.module2.recyclerview.RecyclerViewCommonViewHolder;
import cn.share.jack.module2.refreshload.load.DefaultLoadingView;
import cn.share.jack.module2.refreshload.load.PullUpLoadingRecyclerView;
import cn.share.jack.module2.refreshload.refresh.DropDownRefreshRecyclerView;
import cn.share.jack.test.R;
import cn.share.jack.test.base.BaseActivity;
import cn.share.jack.test.network.NetworkUtil;
import cn.share.jack.test.recyclerview.itemdecoration.LinearLayoutItemDecoration;

/**
 * Created by jack on 2018/5/16
 */

public class RecyclerViewActivity extends BaseActivity {

    @ViewById(R.id.ar_recyclerview)
    PullUpLoadingRecyclerView recyclerView;

    private MyRecyclerViewAdapter mAdapter;

    private static final int ACCESS_LOCATION = 0x010101;
    private int targetSdkVersion;
    private boolean isLoadData;

    @Override
    protected void initView() {
        super.initView();
        ViewUtils.inject(this);

        mAdapter = new MyRecyclerViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LinearLayoutItemDecoration(this, R.drawable.item_decoration));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setEmptyView(NetworkUtil.getNetworkErrorView(this));

        recyclerView.setOnRefreshListener(new DropDownRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        recyclerView.setOnLoadMoreListener(new PullUpLoadingRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoad() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getLoadMoreData();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        mAdapter.setOnItemClickListener(new RecyclerOnItemClickListener() {
            @Override
            public void click(int position, View view) {
                mAdapter.removeItem(position);
            }
        });
    }

    @Override
    protected ViewGroup getRootLayout() {
        return findViewById(R.id.ar_ll_main);
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_recyclerview;
    }

    private void getLoadMoreData() {
        List<String> loadMoreList = new ArrayList<>();
        for (int i = 65; i < 91; i++) {
            loadMoreList.add(((char) i) + "");
        }
        mAdapter.appendList(loadMoreList);
        recyclerView.onLoadMoreComplete();
        recyclerView.removeLoadMore();
    }

    private void requestPermission() {
        try {
            final PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (!selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION);
            } else {
                Toast.makeText(this, "有权限", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean selfPermissionGranted(String permission) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                result = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
            } else {
                result = PermissionChecker.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            switch (requestCode) {
                case ACCESS_LOCATION: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "您已获得定位权限", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "您还未获得定位权限", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!isLoadData) {
                                isLoadData = true;
                                showErrorLayout(null);
                            } else {
                                isLoadData = false;
                                getData();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getData() {
        recyclerView.setHaveData(true);
        showContentView();
        List<String> mList = new ArrayList<>();
        for (int i = 65; i < 91; i++) {
            mList.add(((char) i) + "");
        }
        if (!recyclerView.isAddLoadMoreView()) {
            recyclerView.addLoadViewCreator(new DefaultLoadingView());
        }
        mAdapter.setDataList(mList);
        recyclerView.onRefreshComplete();
    }

    class MyRecyclerViewAdapter extends RecyclerViewCommonAdapter<String> {

        public MyRecyclerViewAdapter(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutRes() {
            return R.layout.item_recyclerview;
        }

        @Override
        protected void convert(RecyclerViewCommonViewHolder holder, String s, int position, List<Object> payloads) {
            holder.setText(R.id.ir_tv_text, s);
        }
    }
}