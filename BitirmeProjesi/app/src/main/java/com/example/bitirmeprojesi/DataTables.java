package com.example.bitirmeprojesi;

public enum DataTables
{
    Allergen("Allergen"),
    ComponentAllergen("ComponentAllergen"),
    DietPlan("DietPlan"),
    DietPlanDetail("DietPlanDetail"),
    DietPreference("DietPreference"),
    FoodComponent("FoodComponent"),
    FoodPreference("FoodPreference"),
    Goal("Goal"),
    HealthCondition("HealthCondition"),
    Meal("Meal"),
    MealComponent("MealComponent"),
    Region("Region"),
    User("User"),
    UserAllergy("UserAllergy"),
    UserMeal("UserMeal");

    private final String tableName;

    DataTables(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableName()
    {
        return tableName;
    }

    public static DataTables fromString(String text)
    {
        for (DataTables b : DataTables.values())
        {
            if (b.tableName.equalsIgnoreCase(text))
            {
                return b;
            }
        }
        return null;
    }
}

