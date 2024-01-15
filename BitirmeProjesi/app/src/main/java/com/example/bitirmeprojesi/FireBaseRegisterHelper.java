package com.example.bitirmeprojesi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FireBaseRegisterHelper
{
    FirebaseAuth firebaseAuth;

    public FireBaseRegisterHelper()
    {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registerNewUser(String email, String password,
                                OnCompleteListener<AuthResult> onCompleteListener)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

    public void logIn(String email, String password,
                      OnCompleteListener<AuthResult> onCompleteListener)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);
    }

    public void resetPassword(String email, OnSuccessListener<Void> onSuccessListener,
                              OnFailureListener onFailureListener)
    {
        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}
