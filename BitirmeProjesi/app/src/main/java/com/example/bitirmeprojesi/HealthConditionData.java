package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class HealthConditionData extends Data
{
    private int ConditionID;
    private String ConditionName;

    public HealthConditionData(int conditionID, String conditionName)
    {
        this.ConditionID = conditionID;
        this.ConditionName = conditionName;
    }

    public int getConditionID()
    {
        return ConditionID;
    }

    public void setConditionID(int conditionID)
    {
        this.ConditionID = conditionID;
    }

    public String getConditionName()
    {
        return ConditionName;
    }

    public void setConditionName(String conditionName)
    {
        this.ConditionName = conditionName;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "HealthCondition{" +
                "conditionID=" + ConditionID +
                ", conditionName='" + ConditionName + '\'' +
                '}';
    }
}
