package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

public class FoodComponentData extends Data
{
    private int ComponentID;
    private String Name;
    private String Measure;
    private int Calories;
    private BigDecimal Carbs;
    private BigDecimal Fats;
    private BigDecimal Proteins;
    private String Category;
    private Integer RegionID;

    public FoodComponentData(int componentID, String name, String measure, int calories, BigDecimal carbs,
                             BigDecimal fats, BigDecimal proteins, String category, Integer regionID)
    {
        this.ComponentID = componentID;
        this.Name = name;
        this.Measure = measure;
        this.Calories = calories;
        this.Carbs = carbs;
        this.Fats = fats;
        this.Proteins = proteins;
        this.Category = category;
        this.RegionID = regionID;
    }

    public int getComponentID()
    {
        return ComponentID;
    }

    public void setComponentID(int componentID)
    {
        this.ComponentID = componentID;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        this.Name = name;
    }

    public String getMeasure()
    {
        return Measure;
    }

    public void setMeasure(String measure)
    {
        this.Measure = measure;
    }

    public int getCalories()
    {
        return Calories;
    }

    public void setCalories(int calories)
    {
        this.Calories = calories;
    }

    public BigDecimal getCarbs()
    {
        return Carbs;
    }

    public void setCarbs(BigDecimal carbs)
    {
        this.Carbs = carbs;
    }

    public BigDecimal getFats()
    {
        return Fats;
    }

    public void setFats(BigDecimal fats)
    {
        this.Fats = fats;
    }

    public BigDecimal getProteins()
    {
        return Proteins;
    }

    public void setProteins(BigDecimal proteins)
    {
        this.Proteins = proteins;
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
        return "FoodComponent{" +
                "componentID=" + ComponentID +
                ", name='" + Name + '\'' +
                ", measure='" + Measure + '\'' +
                ", calories=" + Calories +
                ", carbs=" + Carbs +
                ", fats=" + Fats +
                ", proteins=" + Proteins +
                ", category='" + Category + '\'' +
                ", regionID=" + RegionID +
                '}';
    }
}
