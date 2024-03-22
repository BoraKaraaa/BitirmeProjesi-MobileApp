package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

public class DietPlanDetailData extends Data
{
    enum MealType { BREAKFAST, LUNCH, DINNER, SNACK }

    private int DetailID;
    private int DietPlanID;
    private int MealID;
    private BigDecimal ServingSize;
    private MealType MealType;

    public DietPlanDetailData(int detailID, int dietPlanID, int mealID, BigDecimal servingSize, MealType mealType)
    {
        this.DetailID = detailID;
        this.DietPlanID = dietPlanID;
        this.MealID = mealID;
        this.ServingSize = servingSize;
        this.MealType = mealType;
    }

    public int getDetailID()
    {
        return DetailID;
    }

    public void setDetailID(int detailID)
    {
        this.DetailID = detailID;
    }

    public int getDietPlanID() {
        return DietPlanID;
    }

    public void setDietPlanID(int dietPlanID)
    {
        this.DietPlanID = dietPlanID;
    }

    public int getMealID()
    {
        return MealID;
    }

    public void setMealID(int mealID)
    {
        this.MealID = mealID;
    }

    public BigDecimal getServingSize()
    {
        return ServingSize;
    }

    public void setServingSize(BigDecimal servingSize)
    {
        this.ServingSize = servingSize;
    }

    public MealType getMealType()
    {
        return MealType;
    }

    public void setMealType(MealType mealType)
    {
        this.MealType = mealType;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "DietPlanDetail{" +
                "detailID=" + DetailID +
                ", dietPlanID=" + DietPlanID +
                ", mealID=" + MealID +
                ", servingSize=" + ServingSize +
                ", mealType='" + MealType + '\'' +
                '}';
    }
}

