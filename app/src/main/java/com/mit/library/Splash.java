package com.mit.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private ImageView imageView;
    Thread timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView=findViewById(R.id.imageView);

        Animation myAnim= AnimationUtils.loadAnimation(this,R.anim.my_anim);
        imageView.startAnimation(myAnim);

        final Intent intent=new Intent(this, LoginActivity.class);

        timer=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }
}
