package cn.share.jack.test;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by jack on 2018/4/24
 */

public class TestActivity extends Activity {

    private static final int ACCESS_LOCATION = 0x010101;
    private int targetSdkVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        requestPermission();

        View view = LayoutInflater.from(this).inflate(R.layout.layout_inflater, findViewById(R.id.at_ll_main));
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
}
