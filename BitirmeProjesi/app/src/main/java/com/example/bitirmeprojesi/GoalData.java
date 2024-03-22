package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class GoalData extends Data
{
    private int GoalID;
    private String GoalName;

    public GoalData(int goalID, String goalName)
    {
        this.GoalID = goalID;
        this.GoalName = goalName;
    }

    public int getGoalID()
    {
        return GoalID;
    }

    public void setGoalID(int goalID)
    {
        this.GoalID = goalID;
    }

    public String getGoalName()
    {
        return GoalName;
    }

    public void setGoalName(String goalName)
    {
        this.GoalName = goalName;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Goal{" +
                "goalID=" + GoalID +
                ", goalName='" + GoalName + '\'' +
                '}';
    }
}
