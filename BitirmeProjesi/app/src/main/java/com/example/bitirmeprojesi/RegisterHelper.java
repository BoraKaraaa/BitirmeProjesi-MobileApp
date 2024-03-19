package com.example.bitirmeprojesi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Pattern;

public class RegisterHelper
{
    public final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_-]{2,15}$";
    public final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$";

    public void backButtonListener(Activity activity, View view)
    {
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.onBackPressed();
            }
        });
    }

    public void clickablePasswordEye(ImageView eyeImage, EditText passwordEditText)
    {
        eyeImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    eyeImage.setImageResource(R.drawable.eye_slash);
                }
                else
                {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeImage.setImageResource(R.drawable.eye);
                }
            }
        });
    }

    public String getProperUserName(EditText userNameEditText) throws RegisterException
    {
        String userName = userNameEditText.getText().toString();

        if (Pattern.matches(USERNAME_REGEX, userName))
        {
            return userName;
        }

        throw new RegisterException(ERegistrationError.INVALID_USERNAME, userNameEditText);
    }

    public String getProperEmail(EditText emailEditText) throws RegisterException
    {
        String email = emailEditText.getText().toString();

        if (Pattern.matches(EMAIL_REGEX, email))
        {
            return email;
        }

        throw new RegisterException(ERegistrationError.INVALID_EMAIL, emailEditText);
    }

    public String getProperPassword(EditText passwordEditText) throws RegisterException
    {
        String password = passwordEditText.getText().toString();

        if (Pattern.matches(PASSWORD_REGEX, password))
        {
            return password;
        }

        throw new RegisterException(ERegistrationError.INVALID_PASSWORD, passwordEditText);
    }

    public boolean comparePasswords(String password, String confirmPassword,
                                    EditText confirmPasswordEditText) throws RegisterException
    {
        if (password.compareTo(confirmPassword) != 0)
        {
            throw new RegisterException(ERegistrationError.PASSWORDS_DO_NOT_MATCH, confirmPasswordEditText);
        }

        return true;
    }

    public void setEditTextDefault(EditText editText, String defaultString)
    {
        editText.setHintTextColor(Color.GRAY);
        editText.setHint(defaultString);
    }

    public void handleInvalidEditTextInput(String errorMessage, EditText editTextUserName)
    {
        editTextUserName.setText("");
        editTextUserName.setHintTextColor(Color.RED);
        editTextUserName.setHint(errorMessage);
    }
}
