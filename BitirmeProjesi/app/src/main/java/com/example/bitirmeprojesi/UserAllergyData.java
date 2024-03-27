package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class UserAllergyData extends Data
{
    private String UserID;
    private int AllergenID;

    public UserAllergyData(String userID, int allergenID)
    {
        this.UserID = userID;
        this.AllergenID = allergenID;
    }

    public String getUserID()
    {
        return UserID;
    }

    public void setUserID(String userID)
    {
        this.UserID = userID;
    }

    public int getAllergenID()
    {
        return AllergenID;
    }

    public void setAllergenID(int allergenID)
    {
        this.AllergenID = allergenID;
    }
    @NonNull
    @Override
    public String toString()
    {
        return "UserAllergyData{" +
                "userID=" + UserID +
                ", allergenID='" + AllergenID + '\'' +
                '}';
    }
}
