package cn.share.jack.module2.imageloader;

import android.graphics.Bitmap;

/**
 * Created by jack on 2018/3/15
 * 双缓存
 */

public class DoubleCache implements ImageCache{

    private MemoryCache mMemoryCache;
    private DiskCache mDiskCache;

    public DoubleCache() {
        mMemoryCache =new MemoryCache();
        mDiskCache=new DiskCache();
    }

    @Override
    public Bitmap get(String imageUrl){
        Bitmap bitmap= mMemoryCache.get(imageUrl);
        if (null==bitmap){
            bitmap=mDiskCache.get(imageUrl);
        }
        return  bitmap;
    }

    @Override
    public void put (String imageUrl,Bitmap bitmap){
        mMemoryCache.put(imageUrl,bitmap);
        mDiskCache.put(imageUrl,bitmap);
    }

}
