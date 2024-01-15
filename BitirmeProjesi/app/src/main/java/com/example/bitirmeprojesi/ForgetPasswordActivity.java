package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;


public class ForgetPasswordActivity extends AppCompatActivity
{
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initViews();
    }

    private void initViews()
    {
        backButton = findViewById(R.id.back_button);
    }
}