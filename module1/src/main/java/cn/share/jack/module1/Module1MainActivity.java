package cn.share.jack.module1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



/**
 * Created by jack on 2018/1/11
 */

public class Module1MainActivity extends Activity {

//    public static void start(Context context, String route, NavigationCallback navigationCallback) {
//        ARouteUtil.start(context, route, navigationCallback);
//    }

    private Button buttonJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module1_main);

        boolean login = getIntent().getBooleanExtra("loginactivity", false);

        Toast.makeText(this, "loginactivity---->" + login, Toast.LENGTH_SHORT).show();

        buttonJump = findViewById(R.id.amm_btn_jump);

        buttonJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build("/app/MainActivity").navigation();
            }
        });
    }
}