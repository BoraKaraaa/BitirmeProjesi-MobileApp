package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        setBackImageListener();
    }

    private void initViews()
    {
        backButton = findViewById(R.id.back_button);
    }

    private void setBackImageListener()
    {
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}