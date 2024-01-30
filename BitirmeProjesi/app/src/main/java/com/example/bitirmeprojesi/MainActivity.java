package com.example.bitirmeprojesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    LinearLayout homeLayout, calendarLayout, recipeLayout;
    ImageView homeImage, calendarImage, recipeImage;

    Fragment homeFragment, calendarFragment, recipeFragment;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUserLogInPreference();

        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        recipeFragment = new RecipeFragment();

        initViews();

        setNavBarClickListeners();
    }

    private void setUserLogInPreference()
    {
        sharedPreferences = getSharedPreferences("UserRegistration", MODE_PRIVATE);

        if(!sharedPreferences.getBoolean("isUserLoggedIn", false))
        {
            SharedPreferences.Editor userRegistrationEdit = sharedPreferences.edit();
            userRegistrationEdit.putBoolean("isUserLoggedIn", true);
            userRegistrationEdit.apply();
        }
    }

    private void initViews()
    {
        homeLayout = findViewById(R.id.layout_home);
        calendarLayout = findViewById(R.id.layout_calendar);
        recipeLayout = findViewById(R.id.layout_recipe);

        homeImage = findViewById(R.id.image_home);
        calendarImage = findViewById(R.id.image_calendar);
        recipeImage = findViewById(R.id.image_recipes);
    }

    private void setNavBarClickListeners()
    {
        homeImage.setImageResource(R.drawable.homeb_bot_nav);

        homeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                homeImage.setImageResource(R.drawable.homeb_bot_nav);

                calendarImage.setImageResource(R.drawable.calendar_edit_bot_nav);
                recipeImage.setImageResource(R.drawable.reserve_bot_nav);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, homeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        calendarLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calendarImage.setImageResource(R.drawable.calendar_editb_bot_nav);

                homeImage.setImageResource(R.drawable.home_bot_nav);
                recipeImage.setImageResource(R.drawable.reserve_bot_nav);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, calendarFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recipeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                recipeImage.setImageResource(R.drawable.reserveb_bot_nav);

                calendarImage.setImageResource(R.drawable.calendar_edit_bot_nav);
                homeImage.setImageResource(R.drawable.home_bot_nav);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, recipeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}