package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.logging.Logger;

public class UserInformation extends AppCompatActivity
{
    ImageView backImage;
    EditText userName, age, height, weight;
    Spinner gender, goal, activityLevel, dietPreference, country, region;
    Button saveUserInformation;

    SharedPreferences userPref;
    GoogleMySQLDataBase googleMySQLDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        googleMySQLDataBase = new GoogleMySQLDataBase(this);

        initViews();
        initSpinnerValues();
        initUserInformation();

        setBackImageListener();
        setSaveButtonListener();
    }

    private void initViews()
    {
        backImage = findViewById(R.id.backImage);
        saveUserInformation = findViewById(R.id.save_user_information_button);

        userName = findViewById(R.id.nameEditText);
        age = findViewById(R.id.ageEditText);
        height = findViewById(R.id.heightEditText);
        weight = findViewById(R.id.weightEditText);

        gender = findViewById(R.id.genderSpinner);
        goal = findViewById(R.id.goalSpinner);
        activityLevel = findViewById(R.id.activityLevelSpinner);
        dietPreference = findViewById(R.id.dietPreferenceSpinner);
        country = findViewById(R.id.countrySpinner);
        region = findViewById(R.id.regionSpinner);
    }

    private void initSpinnerValues()
    {

    }

    private void initUserInformation()
    {
        userPref = getSharedPreferences("User", MODE_PRIVATE);

        String userID = userPref.getString("UserID", "");

        if(!userID.equals(""))
        {
            googleMySQLDataBase.getById(DataTables.User, UserData.class, userID, new DataBase.SingleDataCallback<UserData>()
            {
                @Override
                public void onResponse(UserData data)
                {
                    if(data.getUsername() != null)
                    {
                        userName.setText(data.getUsername());
                    }

                    if(data.getAge() != null)
                    {
                        age.setText(data.getAge());
                    }

                    if(data.getHeight() != null)
                    {
                        height.setText(data.getHeight());
                    }

                    if(data.getWeight() != null)
                    {
                        weight.setText(data.getWeight());
                    }


                    //cont to spinners
                }
            });
        }
    }

    private void setBackImageListener()
    {
        backImage.setOnClickListener(new View.OnClickListener()
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