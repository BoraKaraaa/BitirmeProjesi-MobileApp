package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class DietPreferenceData extends Data
{
    private int DietPreferenceID;
    private String DietPreferenceName;

    public DietPreferenceData(int dietPreferenceID, String dietPreferenceName)
    {
        this.DietPreferenceID = dietPreferenceID;
        this.DietPreferenceName = dietPreferenceName;
    }

    public int getDietPreferenceID()
    {
        return DietPreferenceID;
    }

    public void setDietPreferenceID(int dietPreferenceID)
    {
        this.DietPreferenceID = dietPreferenceID;
    }

    public String getDietPreferenceName()
    {
        return DietPreferenceName;
    }

    public void setDietPreferenceName(String dietPreferenceName)
    {
        this.DietPreferenceName = dietPreferenceName;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "DietPreference{" +
                "dietPreferenceID=" + DietPreferenceID +
                ", dietPreferenceName='" + DietPreferenceName + '\'' +
                '}';
    }
}
