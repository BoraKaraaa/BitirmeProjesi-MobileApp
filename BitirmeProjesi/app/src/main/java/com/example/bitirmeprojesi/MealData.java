package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class MealData extends Data
{
    private int MealID;
    private String MealName;
    private String Category;
    private Integer RegionID;

    public MealData(int mealID, String mealName, String category, Integer regionID)
    {
        this.MealID = mealID;
        this.MealName = mealName;
        this.Category = category;
        this.RegionID = regionID;
    }

    public int getMealID()
    {
        return MealID;
    }

    public void setMealID(int mealID)
    {
        this.MealID = mealID;
    }

    public String getMealName()
    {
        return MealName;
    }

    public void setMealName(String mealName)
    {
        this.MealName = mealName;
    }

    public String getCategory()
    {
        return Category;
    }

    public void setCategory(String category)
    {
        this.Category = category;
    }

    public Integer getRegionID()
    {
        return RegionID;
    }

    public void setRegionID(Integer regionID)
    {
        this.RegionID = regionID;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Meal{" +
                "mealID=" + MealID +
                ", mealName='" + MealName + '\'' +
                ", category='" + Category + '\'' +
                ", regionID=" + RegionID +
                '}';
    }
}
