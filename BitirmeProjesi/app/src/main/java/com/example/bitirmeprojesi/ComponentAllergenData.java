package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class ComponentAllergenData extends Data
{
    private int ComponentID;
    private int AllergenID;

    public ComponentAllergenData(int componentID, int allergenID)
    {
        this.ComponentID = componentID;
        this.AllergenID = allergenID;
    }

    public int getComponentID()
    {
        return ComponentID;
    }

    public void setComponentID(int componentID)
    {
        this.ComponentID = componentID;
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
        return "ComponentAllergenData{" +
                "componentID=" + ComponentID +
                ", allergenID=" + AllergenID +
                '}';
    }
}

