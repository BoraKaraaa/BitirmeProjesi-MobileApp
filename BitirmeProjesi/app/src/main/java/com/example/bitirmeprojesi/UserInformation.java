package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserInformation extends AppCompatActivity
{
    Button saveUserInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        initViews();

        setSaveButtonListener();
    }

    private void initViews()
    {
        saveUserInformation = findViewById(R.id.save_user_information_button);
    }

    private void setSaveButtonListener()
    {
        saveUserInformation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(getIntent().getStringExtra("from").equals("MainActivity"))
                {
                    finish();
                }
                else
                {
                    Intent intent = new Intent(UserInformation.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}