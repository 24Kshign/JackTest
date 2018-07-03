package cn.share.jack.module2.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by jack on 2018/3/15
 */

public class MemoryCache implements ImageCache {

    //图片缓存
    private LruCache<String, Bitmap> mImageCache;

    public MemoryCache() {
        //计算可使用的最大内存
        final int mMaxMemory = (int) (Runtime.getRuntime().maxMemory() / 1204);
        //取1/4的可用内存作为缓存
        final int cacheSize = mMaxMemory / 4;
        //初始化缓存
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public void put(String imageUrl, Bitmap bitmap) {
        mImageCache.put(imageUrl, bitmap);
    }

    @Override
    public Bitmap get(String imageUrl) {
        return mImageCache.get(imageUrl);
    }
}