package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity
{
    EditText editTextEmail, editTextPassword;
    Button buttonSignIn;
    ImageView imageGoogle, imagePasswordToggle;
    ProgressBar progressBar;
    TextView textViewSignUp, textForgetPassword;

    RegisterHelper registerHelper;
    FireBaseRegisterHelper fireBaseRegisterHelper;
    GoogleRegisterHelper googleRegisterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerHelper = new RegisterHelper();
        fireBaseRegisterHelper = new FireBaseRegisterHelper();
        googleRegisterHelper = new GoogleRegisterHelper(this, getApplicationContext());

        initViews();
        updateUI();

        clickablePasswordEye();

        setForgetPasswordTextListener();
        setLogInTextListener();
        setLogInGoogleImageListener();
        setSignUpButtonListener();
    }

    private void initViews()
    {
        editTextEmail = findViewById(R.id.mail_edit_text);
        editTextPassword = findViewById(R.id.password_edit_text);

        buttonSignIn = findViewById(R.id.sign_in_button);

        imageGoogle = findViewById(R.id.google_button);

        imagePasswordToggle = findViewById(R.id.password_toggle);

        progressBar = findViewById(R.id.register_progressbar);

        textViewSignUp = findViewById(R.id.sign_up_text);
        textForgetPassword = findViewById(R.id.forget_password_text);
    }

    private void updateUI()
    {
        String signUp = "<font color='#D9806C'> Sign Up</font>";
        textViewSignUp.setText(Html.fromHtml(textViewSignUp.getText() + signUp));
    }

    private void clickablePasswordEye()
    {
        registerHelper.clickablePasswordEye(imagePasswordToggle, editTextPassword);
    }

    private void setForgetPasswordTextListener()
    {
        textForgetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    private void setLogInTextListener()
    {
        textViewSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setLogInGoogleImageListener()
    {
        googleRegisterHelper.setGoogleRegisterToListener(imageGoogle);
    }

    private void setSignUpButtonListener()
    {
        buttonSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);

                editTextEmail.setHintTextColor(Color.GRAY);
                editTextPassword.setHintTextColor(Color.GRAY);

                editTextEmail.setHint("Mail");
                editTextPassword.setHint("Password");

                try
                {
                    String email = registerHelper.getProperEmail(editTextEmail);
                    String password = registerHelper.getProperPassword(editTextPassword);

                    fireBaseRegisterHelper.logIn(email, password, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            progressBar.setVisibility(View.INVISIBLE);

                            if (task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this, "Authentication Success.",
                                        Toast.LENGTH_SHORT).show();

                                saveCurrentUserID(fireBaseRegisterHelper.firebaseAuth.getUid());

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                catch (RegisterException e)
                {
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(LoginActivity.this, "Something Went Wrong",
                            Toast.LENGTH_SHORT).show();

                    registerHelper.handleInvalidEditTextInput(e.getErrorMessage(), e.getEditText());
                }
            }
        });
    }

    private void saveCurrentUserID(String id)
    {
        SharedPreferences userID = getSharedPreferences("User", MODE_PRIVATE);
        SharedPreferences.Editor userIDEdit = userID.edit();
        userIDEdit.putString("UserID", id);
        userIDEdit.apply();
    }
}