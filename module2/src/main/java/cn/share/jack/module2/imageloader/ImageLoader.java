package cn.share.jack.module2.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jack on 2018/3/14
 * 图片加载
 */

public class ImageLoader {


    //线程池，线程池的数量为CPU的数量
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private ImageCache mImageCache;

    public void setImageCache(ImageCache imageCache) {
        this.mImageCache = imageCache;
    }

    public void displayImage(String imageUrl, ImageView imageView) {
        Bitmap bitmap = mImageCache.get(imageUrl);   //先去缓存拿图片
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //图片没有缓存，去下载
        submitLoadRequest(imageUrl, imageView);
    }

    private void submitLoadRequest(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);   //先去下载图片
                if (null != bitmap) {
                    if (imageView.getTag().equals(imageUrl)) {   //判断tag是否相同
                        imageView.setImageBitmap(bitmap);
                    }
                    mImageCache.put(imageUrl, bitmap);   //加到缓存
                }
            }
        });
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();   //获得一个流
            bitmap = BitmapFactory.decodeStream(inputStream);    //通过流获取一个bitmap
            conn.disconnect();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}