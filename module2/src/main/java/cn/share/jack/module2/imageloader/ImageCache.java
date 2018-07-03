package cn.share.jack.module2.imageloader;

import android.graphics.Bitmap;

/**
 * Created by jack on 2018/3/15
 */

public interface ImageCache {

    Bitmap get(String imageUrl);

    void put(String imageUrl, Bitmap bitmap);
}