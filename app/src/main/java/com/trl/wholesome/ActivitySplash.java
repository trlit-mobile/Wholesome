package com.trl.wholesome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;


public class ActivitySplash extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(ActivitySplash.this, ActivityMainDashboard.class);
                    startActivity(i);
                        overridePendingTransition(R.anim.enter_from_left, R.anim.hold_bottom);
                    finish();

                }
            }, SPLASH_TIME_OUT);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
