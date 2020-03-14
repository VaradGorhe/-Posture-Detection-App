package com.example.posturedetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo=findViewById(R.id.logo);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);
        logo.setAnimation(animation);

        tag=findViewById(R.id.tag);
        animation= AnimationUtils.loadAnimation(this,R.anim.tagline);
        tag.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                Intent openMainActivity =  new Intent(SplashScreen.this,Introduction.class);
                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);

                startActivity(openMainActivity);
                finish();

            }
        }, 3000);
    }
}
