package com.example.bitirmeprojesi;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class GoogleRegisterHelper
{
    private SignInClient oneTapClient;
    private BeginSignInRequest signUpRequest;
    private ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;
    private Activity activity;
    private Context appContext;

    public GoogleRegisterHelper(AppCompatActivity activity, Context appContext)
    {
        this.activity = activity;
        this.appContext = appContext;

        oneTapClient = Identity.getSignInClient(activity);

        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            .setServerClientId(activity.getString(R.string.web_client_id))
                            .setFilterByAuthorizedAccounts(false)
                            .build()
            )
            .build();


        activityResultLauncher =
            activity.registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
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
                                    Toast.makeText(appContext,
                                            "Email: " + email, Toast.LENGTH_SHORT).show();
                                }
                            } catch (ApiException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void setGoogleRegisterToListener(View view)
    {
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                oneTapClient.beginSignIn(signUpRequest)
                    .addOnSuccessListener(activity, new OnSuccessListener<BeginSignInResult>()
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
                    .addOnFailureListener(activity, new OnFailureListener()
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
}
