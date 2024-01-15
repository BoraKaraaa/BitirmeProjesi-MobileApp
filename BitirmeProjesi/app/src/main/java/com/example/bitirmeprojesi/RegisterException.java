package com.example.bitirmeprojesi;

import android.widget.EditText;

public class RegisterException extends RuntimeException
{
    private final ERegistrationError error;
    private final EditText editText;

    public RegisterException(ERegistrationError error, EditText editText)
    {
        super(error.getMessage());

        this.error = error;
        this.editText = editText;
    }

    public ERegistrationError getError() {
        return error;
    }
    public String getErrorMessage() { return getError().getMessage(); }
    public EditText getEditText() { return editText; }
}
