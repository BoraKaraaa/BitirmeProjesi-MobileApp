package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class FoodPreferenceData extends Data
{
    enum PreferenceType { LIKE, DISLIKE }

    private int PreferenceID;
    private int UserID;
    private int ComponentID;
    private PreferenceType PreferenceType;

    public FoodPreferenceData(int preferenceID, int userID, int componentID, PreferenceType preferenceType)
    {
        this.PreferenceID = preferenceID;
        this.UserID = userID;
        this.ComponentID = componentID;
        this.PreferenceType = preferenceType;
    }

    public int getPreferenceID()
    {
        return PreferenceID;
    }

    public void setPreferenceID(int preferenceID)
    {
        this.PreferenceID = preferenceID;
    }

    public int getUserID()
    {
        return UserID;
    }

    public void setUserID(int userID)
    {
        this.UserID = userID;
    }

    public int getComponentID()
    {
        return ComponentID;
    }

    public void setComponentID(int componentID)
    {
        this.ComponentID = componentID;
    }

    public PreferenceType getPreferenceType()
    {
        return PreferenceType;
    }

    public void setPreferenceType(PreferenceType preferenceType)
    {
        this.PreferenceType = preferenceType;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "FoodPreference{" +
                "preferenceID=" + PreferenceID +
                ", userID=" + UserID +
                ", componentID=" + ComponentID +
                ", preferenceType=" + PreferenceType +
                '}';
    }
}
