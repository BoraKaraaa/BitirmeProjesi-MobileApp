package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class AllergenData extends Data
{
    private int AllergenID;
    private String AllergenName;

    public AllergenData(int allergenID, String allergenName)
    {
        this.AllergenID = allergenID;
        this.AllergenName = allergenName;
    }

    public int getAllergenID()
    {
        return AllergenID;
    }

    public void setAllergenID(int allergenID)
    {
        this.AllergenID = allergenID;
    }

    public String getAllergenName()
    {
        return AllergenName;
    }

    public void setAllergenName(String allergenName)
    {
        this.AllergenName = allergenName;
    }
    @NonNull
    @Override
    public String toString()
    {
        return "AllergenData{" +
                "allergenID=" + AllergenID +
                ", allergenName='" + AllergenName + '\'' +
                '}';
    }

}

