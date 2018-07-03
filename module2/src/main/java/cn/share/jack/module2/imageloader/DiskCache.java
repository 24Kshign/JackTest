package cn.share.jack.module2.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jack on 2018/3/15
 */

public class DiskCache implements ImageCache {

    static String cacheDIr = "sdcard/cache/";

    @Override
    public Bitmap get(String imageUrl) {
        return BitmapFactory.decodeFile(cacheDIr + imageUrl);
    }

    @Override
    public void put(String imageUrl, Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDIr + imageUrl);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
