package cn.share.jack.module2.refreshload.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.share.jack.module2.R;


/**
 * Created by jack on 2018/6/8
 */
public class DefaultRefreshView extends RefreshViewCreator {

    private TextView tvRefreshView;
    private ImageView ivArrow;
    private ProgressBar progressBar;
    private boolean isArrowUp;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private int mRotateAniTime = 150;

    @Override
    public View getRefreshView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.default_refresh_header, parent, false);
        tvRefreshView = refreshView.findViewById(R.id.drh_tv_refresh_view);
        ivArrow = refreshView.findViewById(R.id.drh_iv_arrow);
        progressBar = refreshView.findViewById(R.id.drh_progress);
        buildAnimation();
        return refreshView;
    }

    private void buildAnimation() {
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);
    }

    @Override
    public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshState) {
        float rotate = ((float) currentDragHeight) / refreshViewHeight;
        ivArrow.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        if (rotate >= 0 && currentRefreshState == DropDownRefreshRecyclerView.REFRESH_STATUS_LOOSEN_REFRESHING && !isArrowUp) {
            isArrowUp = true;
            tvRefreshView.setText("释放刷新");
            ivArrow.clearAnimation();
            ivArrow.startAnimation(mFlipAnimation);
        } else if (rotate < 0 && currentRefreshState == DropDownRefreshRecyclerView.REFRESH_STATUS_PULL_DOWN_REFRESH && isArrowUp) {
            isArrowUp = false;
            ivArrow.clearAnimation();
            ivArrow.startAnimation(mReverseFlipAnimation);
            tvRefreshView.setText("下拉刷新");
        }
    }

    @Override
    public void onRefreshing() {
        ivArrow.clearAnimation();
        ivArrow.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvRefreshView.setText("正在刷新...");
    }

    @Override
    public void onStopRefresh() {
        ivArrow.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tvRefreshView.setText("刷新完成");
    }

    @Override
    public void cancelRefresh() {
        tvRefreshView.setText("下拉刷新");
        if (isArrowUp) {
            isArrowUp = false;
            ivArrow.clearAnimation();
            ivArrow.startAnimation(mReverseFlipAnimation);
        }
    }
}
