package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

import java.util.Date;

public class DietPlanData extends Data
{
    private int DietPlanID;
    private String UserID;
    private Date PlanDate;

    public DietPlanData(int dietPlanID, String userID, Date planDate)
    {
        this.DietPlanID = dietPlanID;
        this.UserID = userID;
        this.PlanDate = planDate;
    }

    public int getDietPlanID()
    {
        return DietPlanID;
    }

    public void setDietPlanID(int dietPlanID)
    {
        this.DietPlanID = dietPlanID;
    }

    public String getUserID()
    {
        return UserID;
    }

    public void setUserID(String userID)
    {
        this.UserID = userID;
    }

    public Date getPlanDate()
    {
        return PlanDate;
    }

    public void setPlanDate(Date planDate)
    {
        this.PlanDate = planDate;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "DietPlanData{" +
                "dietPlanID=" + DietPlanID +
                ", userID='" + UserID + '\'' +
                ", planDate=" + PlanDate +
                '}';
    }
}

