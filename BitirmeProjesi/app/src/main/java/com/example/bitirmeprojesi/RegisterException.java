package com.example.bitirmeprojesi;

public class RegisterException extends RuntimeException
{
    private final ERegistrationError error;

    public RegisterException(ERegistrationError error)
    {
        super(error.getMessage());
        this.error = error;
    }

    public ERegistrationError getError() {
        return error;
    }
    public String getErrorMessage() { return getError().getMessage(); }
}
