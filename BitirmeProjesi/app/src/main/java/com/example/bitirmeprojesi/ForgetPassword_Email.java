package com.example.bitirmeprojesi;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class ForgetPassword_Email extends Fragment
{
    private Button resetPasswordButton;
    private EditText emailEditText;
    private ProgressBar progressBar;

    private RegisterHelper registerHelper;
    private FireBaseRegisterHelper fireBaseRegisterHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_forget_password__email, container, false);

        registerHelper = new RegisterHelper();
        fireBaseRegisterHelper = new FireBaseRegisterHelper();

        initViews(view);

        setResetPasswordButtonListener();

        return view;
    }

    private void initViews(View view)
    {
        resetPasswordButton = view.findViewById(R.id.forget_password_reset_password_button);
        emailEditText = view.findViewById(R.id.forget_password_email_edit_text);
        progressBar = view.findViewById(R.id.register_progressbar);
    }

    private void setResetPasswordButtonListener()
    {
        resetPasswordButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    String email = registerHelper.getProperEmail(emailEditText);

                    progressBar.setVisibility(View.VISIBLE);

                    fireBaseRegisterHelper.resetPassword(email, new OnSuccessListener<Void>()
                    {
                        @Override
                        public void onSuccess(Void unused)
                        {
                            Toast.makeText(getActivity(),
                                    "Reset Password link has been sent to your registered Email",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }, new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(getActivity(),
                                    "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                catch (RegisterException e)
                {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), "Something Went Wrong",
                            Toast.LENGTH_SHORT).show();

                    registerHelper.handleInvalidEditTextInput(e.getErrorMessage(), e.getEditText());
                }
            }
        });
    }
}
