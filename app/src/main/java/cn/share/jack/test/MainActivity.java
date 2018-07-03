package cn.share.jack.test;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.share.jack.module2.dialog.FRDialog;
import cn.share.jack.module2.ioc.ViewById;
import cn.share.jack.module2.ioc.ViewUtils;

//@Route(path = "/app/MainActivity")
public class MainActivity extends FragmentActivity {

    //    @ViewById(R.id.am_btn_jump_login)
//    Button btnJumpLogin;
    @ViewById(R.id.am_rating_bar)
    CustomRatingBar customRatingBar;
    @ViewById(R.id.am_tv_text)
    TextView tvText;

    private Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    private static final String KEY = "34E0D9A564E74FDCC8A14A60F6B8BDF8DBD6E08B0D6B14EB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

//        FragmentManager fragmentManager=getSupportFragmentManager();
//        //开启一个事物
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.xxx,new Fragment());
//        //提交事物
//        fragmentTransaction.commit();

        customRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick --> ");
            }
        });

//        customRatingBar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("TAG", "onTouch --> " + event.getAction());
//                return false;
//            }
//        });

        new Thread(new Runnable() {
            @Override
            public void run() {
//                Log.e("TAG", "Runnable: " + (Looper.myLooper() == Looper.getMainLooper() ? "主线程" : "子线程"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Message msg = Message.obtain();
//                msg.obj = "点我";
//                handler.sendMessage(msg);
//                Looper.prepare();
//                Handler handler1=new Handler();
                tvText.setText("你好啊");
            }
        }).start();

//        OkHttpClient okHttpClient=new OkHttpClient();
//        Request request=new Request.Builder()
//                .url("http://www.baidu.com")
//                .get()
//                        .build();
//        Call call=okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Log.e("WindowFocusChanged", "width=" + btnJumpLogin.getWidth() + "\nheight=" + btnJumpLogin.getHeight());
    }

//    @OnClick(R.id.am_btn_jump_login)
//    public void onClick(View v) {
////        startActivity(new Intent(MainActivity.this, LoveActivity.class));
//    }

    public void getAllAppNames() {
        PackageManager pm = getPackageManager();
        //PackageManager.GET_UNINSTALLED_PACKAGES==8192
        List<PackageInfo> list2 = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        //PackageManager.GET_SHARED_LIBRARY_FILES==1024
        //List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_SHARED_LIBRARY_FILES);
        //PackageManager.GET_META_DATA==128
        //List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_META_DATA);
        //List<PackageInfo> list2=pm.getInstalledPackages(0);
        //List<PackageInfo> list2=pm.getInstalledPackages(-10);
        //List<PackageInfo> list2=pm.getInstalledPackages(10000);
        for (PackageInfo packageInfo : list2) {
            //得到手机上已经安装的应用的名字,即在AndriodMainfest.xml中的app_name。
            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            //得到应用所在包的名字,即在AndriodMainfest.xml中的package的值。
            String packageName = packageInfo.packageName;
            Log.e("TAG", "应用的名字：" + appName + "    应用的包名：" + packageName);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    private void showCheckoutDialog() {

        final FRDialog dialog = new FRDialog.Builder(MainActivity.this)
                .setContentView(R.layout.dialog_check_update)
                .setText(R.id.dcu_tv_content, "1.文字文字我是文字文字文字我是文字文字文字我是文字！\n2.文字文字文字文字文字\n3.文字文字文字文字文字")
                .setDefaultAnimation()
                .show();
        dialog.setOnClickListener(R.id.dcu_tv_update, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
