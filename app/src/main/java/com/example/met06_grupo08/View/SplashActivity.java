package com.example.met06_grupo08.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.met06_grupo08.R;

/**
 * Simple Splash (loading screen)
 */
public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2000;
    Bundle extras;
    String fromNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable(){


            @Override
            public void run() {
                /* Creating an Intent to start another activity
                 * After starting MainpageActivity i am closing this (SplashscreenActivity)
                 * Activity.
                 */

                startActivity(new Intent(SplashActivity.this, MenuActivityNavigation.class));
                finish();
            }

        },SPLASH_TIME_OUT);
    }
}
