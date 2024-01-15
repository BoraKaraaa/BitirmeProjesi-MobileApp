package com.example.bitirmeprojesi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FireBaseRegisterHelper
{
    FirebaseAuth mAuth;
    public FireBaseRegisterHelper()
    {
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerNewUser(String email, String password,
                                OnCompleteListener<AuthResult> onCompleteListener)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

    public void logIn(String email, String password,
                      OnCompleteListener<AuthResult> onCompleteListener)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener);
    }
}
