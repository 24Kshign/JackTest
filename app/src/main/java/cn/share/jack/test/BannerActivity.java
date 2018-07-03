package cn.share.jack.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.share.jack.module2.banner.BannerAdapter;
import cn.share.jack.module2.banner.BannerView;
import cn.share.jack.module2.ioc.ViewById;
import cn.share.jack.module2.ioc.ViewUtils;

/**
 * Created by jack on 2018/5/7
 */

public class BannerActivity extends Activity {

    @ViewById(R.id.ab_banner_vp)
    BannerView bannerView;

    private static final String[] images = {
            "http://pic29.photophoto.cn/20131204/0034034499213463_b.jpg"
            , "http://imgsrc.baidu.com/imgad/pic/item/34fae6cd7b899e51fab3e9c048a7d933c8950d21.jpg"
            , "http://imgsrc.baidu.com/imgad/pic/item/4afbfbedab64034f788a1cd2a5c379310b551d9a.jpg"
            , "http://pic21.photophoto.cn/20111106/0020032891433708_b.jpg"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ViewUtils.inject(this);

        bannerView.setIndicatorNormalDrawable(ContextCompat.getDrawable(this, R.drawable.dot_unselected));   //未选中
        bannerView.setIndicatorFocusDrawable(ContextCompat.getDrawable(this, R.drawable.dot_selected));   //已选中
        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position) {
                ImageView iv = new ImageView(BannerActivity.this);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(BannerActivity.this).load(images[position]).into(iv);
                return iv;
            }

            @Override
            public int getCount() {
                return images.length;
            }
        });

        bannerView.startRoll();
    }
}