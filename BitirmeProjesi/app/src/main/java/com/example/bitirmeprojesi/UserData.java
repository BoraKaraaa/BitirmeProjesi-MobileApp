package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;

public class UserData extends Data
{
    private String UserID;
    private String Username;
    private String Email;
    private String Gender;
    private Integer Age;
    private Integer Height;
    private Integer Weight;
    private String ActivityLevel;
    private Integer GoalID;
    private Integer DietPreferenceID;
    private Integer RegionID;

    // Constructor
    public UserData(String userID, String username, String email, String gender, Integer age,
                    Integer height, Integer weight, String activityLevel, Integer goalID,
                    Integer dietPreferenceID, Integer regionID)
    {
        this.UserID = userID;
        this.Username = username;
        this.Email = email;
        this.Gender = gender;
        this.Age = age;
        this.Height = height;
        this.Weight = weight;
        this.ActivityLevel = activityLevel;
        this.GoalID = goalID;
        this.DietPreferenceID = dietPreferenceID;
        this.RegionID = regionID;
    }

    // Getters and Setters
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        this.Age = age;
    }

    public Integer getHeight() {
        return Height;
    }

    public void setHeight(Integer height) {
        this.Height = height;
    }

    public Integer getWeight() {
        return Weight;
    }

    public void setWeight(Integer weight) {
        this.Weight = weight;
    }

    public String getActivityLevel() {
        return ActivityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.ActivityLevel = activityLevel;
    }

    public Integer getGoalID() {
        return GoalID;
    }

    public void setGoalID(Integer goalID) {
        this.GoalID = goalID;
    }

    public Integer getDietPreferenceID() {
        return DietPreferenceID;
    }

    public void setDietPreferenceID(Integer dietPreferenceID) {
        this.DietPreferenceID = dietPreferenceID;
    }

    public Integer getRegionID() {
        return RegionID;
    }

    public void setRegionID(Integer regionID) {
        this.RegionID = regionID;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserData{" +
                "UserID=" + UserID +
                ", Username='" + Username + '\'' +
                ", Email='" + Email + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Age=" + Age +
                ", Height=" + Height +
                ", Weight=" + Weight +
                ", ActivityLevel='" + ActivityLevel + '\'' +
                ", GoalID=" + GoalID +
                ", DietPreferenceID=" + DietPreferenceID +
                ", RegionID=" + RegionID +
                '}';
    }
}