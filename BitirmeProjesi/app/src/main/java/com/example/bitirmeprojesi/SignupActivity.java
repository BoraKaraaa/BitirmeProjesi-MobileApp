package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class SignupActivity extends AppCompatActivity
{
    EditText editTextUserName, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonSignUp;
    ImageView imageGoogle, imagePasswordToggle, imageConfirmPasswordToggle;
    ProgressBar progressBar;
    TextView textViewLogIn;

    RegisterHelper registerHelper;
    FireBaseRegisterHelper fireBaseRegisterHelper;
    GoogleRegisterHelper googleRegisterHelper;
    GoogleMySQLDataBase googleMySQLDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        registerHelper = new RegisterHelper();
        fireBaseRegisterHelper = new FireBaseRegisterHelper();
        googleRegisterHelper = new GoogleRegisterHelper(this, getApplicationContext());
        googleMySQLDataBase = new GoogleMySQLDataBase(this);

        initViews();
        updateUI();

        clickablePasswordEye();

        setLogInTextListener();
        setLogInGoogleImageListener();
        setSignUpButtonListener();
    }

    private void initViews()
    {
        editTextUserName = findViewById(R.id.username_edit_text);
        editTextEmail = findViewById(R.id.mail_edit_text);
        editTextPassword = findViewById(R.id.password_edit_text);
        editTextConfirmPassword = findViewById(R.id.confirm_password_edit_text);

        buttonSignUp = findViewById(R.id.sign_up_button);

        imageGoogle = findViewById(R.id.google_button);

        imagePasswordToggle = findViewById(R.id.password_toggle);
        imageConfirmPasswordToggle = findViewById(R.id.confirm_password_toggle);

        progressBar = findViewById(R.id.register_progressbar);

        textViewLogIn = findViewById(R.id.login_text);
    }

    private void updateUI()
    {
        String signIn = "<font color='#D9806C'> Sign In</font>";
        textViewLogIn.setText(Html.fromHtml(textViewLogIn.getText() + signIn));
    }

    private void clickablePasswordEye()
    {
        registerHelper.clickablePasswordEye(imagePasswordToggle, editTextPassword);
        registerHelper.clickablePasswordEye(imageConfirmPasswordToggle, editTextConfirmPassword);
    }

    private void setLogInTextListener()
    {
        textViewLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
        buttonSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);

                registerHelper.setEditTextDefault(editTextUserName, "UserName");
                registerHelper.setEditTextDefault(editTextEmail, "Mail");
                registerHelper.setEditTextDefault(editTextPassword, "Password");
                registerHelper.setEditTextDefault(editTextConfirmPassword, "");

                try
                {
                    String userName = registerHelper.getProperUserName(editTextUserName);
                    String email = registerHelper.getProperEmail(editTextEmail);
                    String password = registerHelper.getProperPassword(editTextPassword);

                    String confirmPassword = editTextConfirmPassword.getText().toString();

                    registerHelper.comparePasswords(password, confirmPassword, editTextConfirmPassword);

                    fireBaseRegisterHelper.registerNewUser(email, password, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful())
                            {
                                Toast.makeText(SignupActivity.this, "Account Created.",
                                        Toast.LENGTH_SHORT).show();

                                UserData userData = new UserData(fireBaseRegisterHelper.firebaseAuth.getUid(),
                                        userName, email);
                                googleMySQLDataBase.insert(DataTables.User, UserData.class, userData);

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                catch (RegisterException e)
                {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(SignupActivity.this, "Something Went Wrong",
                            Toast.LENGTH_SHORT).show();

                    registerHelper.handleInvalidEditTextInput(e.getErrorMessage(), e.getEditText());
                }
            }
        });
    }
}
