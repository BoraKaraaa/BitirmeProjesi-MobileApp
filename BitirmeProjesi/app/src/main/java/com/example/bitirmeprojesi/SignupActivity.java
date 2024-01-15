package com.example.bitirmeprojesi;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity
{
    private final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_-]{2,15}$";
    private final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$";

    EditText editTextUserName, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonSignUp;
    ImageView imageGoogle, imagePasswordToggle, imageConfirmPasswordToggle;
    ProgressBar progressBar;
    TextView textViewLogIn;
    FirebaseAuth mAuth;

    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

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

        imageConfirmPasswordToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (editTextConfirmPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                {
                    editTextConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    imageConfirmPasswordToggle.setImageResource(R.drawable.eye_slash);
                }
                else
                {
                    editTextConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imageConfirmPasswordToggle.setImageResource(R.drawable.eye);
                }
            }
        });
    }

    private String getProperUserName() throws RegisterException
    {
        String userName = editTextUserName.getText().toString();

        if (Pattern.matches(USERNAME_REGEX, userName))
        {
            return userName;
        }

        throw new RegisterException(ERegistrationError.INVALID_USERNAME);
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
        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.web_client_id))
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build();

        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        try
                        {
                            SignInCredential credential = oneTapClient
                                    .getSignInCredentialFromIntent(result.getData());

                            String idToken = credential.getGoogleIdToken();

                            if (idToken !=  null)
                            {
                                String email = credential.getId();
                                Toast.makeText(getApplicationContext(),
                                        "Email: " + email, Toast.LENGTH_SHORT).show();
                            }
                        } catch (ApiException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });

        imageGoogle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                oneTapClient.beginSignIn(signUpRequest)
                    .addOnSuccessListener(SignupActivity.this, new OnSuccessListener<BeginSignInResult>()
                    {
                        @Override
                        public void onSuccess(BeginSignInResult result)
                        {
                            IntentSenderRequest intentSenderRequest
                                = new IntentSenderRequest.Builder(
                                        result.getPendingIntent().getIntentSender()).build();

                            activityResultLauncher.launch(intentSenderRequest);
                        }
                    })
                    .addOnFailureListener(SignupActivity.this, new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            // No Google Accounts found. Just continue presenting the signed-out UI.
                            Log.d(TAG, e.getLocalizedMessage());
                        }
                    });
            }
        });
    }

    private void setSignUpButtonListener()
    {
        buttonSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);

                editTextUserName.setHintTextColor(Color.GRAY);
                editTextEmail.setHintTextColor(Color.GRAY);
                editTextPassword.setHintTextColor(Color.GRAY);
                editTextConfirmPassword.setHintTextColor(Color.GRAY);

                editTextUserName.setHint("UserName");
                editTextEmail.setHint("Mail");
                editTextPassword.setHint("Password");
                editTextConfirmPassword.setHint("");

                try
                {
                    String userName = getProperUserName();
                    String email = getProperEmail();
                    String password = getProperPassword();

                    String confirmPassword = editTextConfirmPassword.getText().toString();

                    if (password.compareTo(confirmPassword) != 0)
                    {
                        throw new RegisterException(ERegistrationError.PASSWORDS_DO_NOT_MATCH);
                    }

                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful())
                                {
                                    Toast.makeText(SignupActivity.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();

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

                    switch (e.getError())
                    {
                        case INVALID_USERNAME:
                            handleInvalidUserName(e);
                            break;
                        case INVALID_EMAIL:
                            handleInvalidEmail(e);
                            break;
                        case INVALID_PASSWORD:
                            handleInvalidPassword(e);
                            break;
                        case PASSWORDS_DO_NOT_MATCH:
                            handleMissMatchPassword(e);
                            break;
                    }
                }
            }
        });
    }

    private void handleInvalidUserName(RegisterException e)
    {
        editTextUserName.setText("");
        editTextUserName.setHintTextColor(Color.RED);
        editTextUserName.setHint(e.getErrorMessage());
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

    private void handleMissMatchPassword(RegisterException e)
    {
        editTextConfirmPassword.setText("");
        editTextConfirmPassword.setHintTextColor(Color.RED);
        editTextConfirmPassword.setHint(e.getErrorMessage());
    }
}
