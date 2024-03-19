package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity
{
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(!checkUserLoggedInBefore())
                {
                    Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }

    private boolean checkUserLoggedInBefore()
    {
        sharedPreferences = getSharedPreferences("UserRegistration", MODE_PRIVATE);
        boolean isUserLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false);

        if(isUserLoggedIn)
        {
            //Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            Intent intent = new Intent(SplashActivity.this, SignupActivity.class); // Debug
            //Intent intent = new Intent(SplashActivity.this, UserInformation.class); // Debug
            startActivity(intent);
            finish();

            return true;
        }

        return false;
    }
}