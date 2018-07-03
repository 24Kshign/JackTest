package cn.share.jack.module2.refreshload.load;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.share.jack.module2.R;



/**
 * Created by jack on 2018/6/8
 */
public class DefaultLoadingView extends LoadViewCreator {

    private TextView tvFooter;
    private ProgressBar progressBar;

    private boolean isNeedRelease = true;

    @Override
    public View getLoadView(Context context, ViewGroup parent) {
        View loadMoreView = LayoutInflater.from(context).inflate(R.layout.default_loadmore_footer, parent, false);
        tvFooter = loadMoreView.findViewById(R.id.default_loadmore_footer_tv);
        progressBar = loadMoreView.findViewById(R.id.default_loadmore_footer_progressbar);
        return loadMoreView;
    }

    @Override
    public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshState) {
        float rotate = ((float) currentDragHeight) / refreshViewHeight;
        progressBar.setVisibility(View.GONE);
        if (rotate >= 1 && currentRefreshState == PullUpLoadingRecyclerView.LOAD_STATUS_LOOSEN_LOADING && isNeedRelease) {
            isNeedRelease = false;
            tvFooter.setText("松开加载更多...");
        } else if (rotate < 1 && currentRefreshState == PullUpLoadingRecyclerView.LOAD_STATUS_PULL_DOWN_REFRESH && !isNeedRelease) {
            isNeedRelease = true;
            tvFooter.setText("上拉加载更多");
        }
    }

    @Override
    public void onLoading() {
        progressBar.setVisibility(View.VISIBLE);
        tvFooter.setText("正在加载更多...");
    }

    @Override
    public void onStopLoad() {
        progressBar.setVisibility(View.GONE);
        tvFooter.setText("上拉加载更多");
    }

    @Override
    public void onNoMoreData() {
        tvFooter.setText("——— 我是有底线的 ———");
    }
}
