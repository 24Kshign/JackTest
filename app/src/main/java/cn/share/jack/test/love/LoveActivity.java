package cn.share.jack.test.love;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.share.jack.test.R;

/**
 * Created by jack on 2018/2/6
 */

public class LoveActivity extends Activity {

    private BesselLoveUIComponent besselLoveUIComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        besselLoveUIComponent=findViewById(R.id.al_bessel_love);
    }

    public void addLove(View view){
        besselLoveUIComponent.addLove();
    }
}
