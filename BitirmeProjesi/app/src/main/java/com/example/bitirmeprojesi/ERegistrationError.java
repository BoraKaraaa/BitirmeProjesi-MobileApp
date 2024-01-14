package com.example.bitirmeprojesi;

public enum ERegistrationError
{
    INVALID_USERNAME("Invalid UserName"),
    INVALID_EMAIL("Invalid Email"),
    INVALID_PASSWORD("Invalid Password"),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match");

    private final String message;

    ERegistrationError(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
