package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity
{
    private final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$";

    EditText editTextEmail, editTextPassword;
    Button buttonSignIn;
    ImageView imageGoogle, imageFacebook, imagePasswordToggle;
    ProgressBar progressBar;
    TextView textViewSignUp, textForgetPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initViews();
        updateUI();

        clickablePasswordEye();

        setForgetPasswordTextListener();
        setLogInTextListener();
        setSignUpButtonListener();
    }

    private void initViews()
    {
        editTextEmail = findViewById(R.id.mail_edit_text);
        editTextPassword = findViewById(R.id.password_edit_text);

        buttonSignIn = findViewById(R.id.sign_in_button);

        imageGoogle = findViewById(R.id.google_button);
        imageFacebook = findViewById(R.id.facebook_button);

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
        imagePasswordToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (editTextPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                {
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    imagePasswordToggle.setImageResource(R.drawable.eye_slash);
                }
                else
                {
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imagePasswordToggle.setImageResource(R.drawable.eye);
                }
            }
        });
    }

    private String getProperEmail() throws RegisterException
    {
        String email = editTextEmail.getText().toString();

        if (Pattern.matches(EMAIL_REGEX, email))
        {
            return email;
        }

        throw new RegisterException(ERegistrationError.INVALID_EMAIL);
    }

    private String getProperPassword() throws RegisterException
    {
        String password = editTextPassword.getText().toString();

        if (Pattern.matches(PASSWORD_REGEX, password))
        {
            return password;
        }

        throw new RegisterException(ERegistrationError.INVALID_PASSWORD);
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

    private void setForgetPasswordTextListener()
    {
        textForgetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
                    String email = getProperEmail();
                    String password = getProperPassword();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    progressBar.setVisibility(View.INVISIBLE);

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(LoginActivity.this, "Authentication Success.",
                                                Toast.LENGTH_SHORT).show();

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

                    switch (e.getError())
                    {
                        case INVALID_EMAIL:
                            handleInvalidEmail(e);
                            break;
                        case INVALID_PASSWORD:
                            handleInvalidPassword(e);
                            break;
                    }
                }
            }
        });
    }

    private void handleInvalidEmail(RegisterException e)
    {
        editTextEmail.setText("");
        editTextEmail.setHintTextColor(Color.RED);
        editTextEmail.setHint(e.getErrorMessage());
    }

    private void handleInvalidPassword(RegisterException e)
    {
        editTextPassword.setText("");
        editTextPassword.setHintTextColor(Color.RED);
        editTextPassword.setHint(e.getErrorMessage());
    }
}