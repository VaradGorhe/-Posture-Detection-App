package com.example.posturedetection;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class Introduction extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Fed up of Back Pain?", "Don't Worry,We are here!",
                R.drawable.sad, ContextCompat.getColor(getApplicationContext(), R.color.orange)));
        addSlide(AppIntroFragment.newInstance("Always be comfortable!", "Easy caliberating options helps you to adjust your sitting position anytime,anywhere.",
                R.drawable.adjust, ContextCompat.getColor(getApplicationContext(), R.color.orange)));
        addSlide(AppIntroFragment.newInstance("Here are your daily postures! ", "The app will let you know your day to day change in the back positions.",
                R.drawable.plot, ContextCompat.getColor(getApplicationContext(), R.color.orange)));


    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
}
