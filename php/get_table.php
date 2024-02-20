<?php

require 'config.php';

$allowedTables = ['Allergen', 'ComponentAllergen', 'DietPlan', 'DietPlanDetail',
 'DietPreference', 'FoodComponent', 'FoodPreference', 'Goal', 'HealthCondition', 
 'Meal', 'MealComponent', 'Region', 'User', 'UserAllergy', 'UserMeal'];

$tableName = isset($_GET['table_name']) ? $_GET['table_name'] : '';

//echo 'Started<br>';

if (in_array($tableName, $allowedTables)) 
{   
    //echo 'Table name founded<br>';

    try 
    {
        //echo 'Try To Connecting...<br>';

        // Cloud SQL Proxy kullanmadan doğrudan bağlanma
        $conn = mysqli_connect(null, "root", "123456bbbB", "diyetisyen", "0", "/cloudsql/bitirmeprojesi-firebase:europe-west9:bitirme");

        if (!$conn) 
        {
            die("Connection failed: " . mysqli_connect_error());
        }

        //echo 'Connected<br>';

        $sql = "SELECT * FROM " . $tableName;
        $result = mysqli_query($conn, $sql);

        if (mysqli_num_rows($result) > 0) 
        {
            //echo 'Query success<br>';

            $data = array();
            while ($row = mysqli_fetch_assoc($result)) 
            {
                // Convert numeric strings to actual numbers
                foreach ($row as $key => $value) 
                {
                    if (is_numeric($value)) 
                    {
                        $row[$key] = intval($value);
                    }
                }
                $data[] = $row;
            }

            echo json_encode($data);
        } 
        else 
        {
            echo json_encode([]);
        }

        //echo 'Close<br>';
        mysqli_close($conn);
    } 
    catch(Exception $e) 
    {
        echo 'Connection Error: ' . $e->getMessage();
    }
}
else 
{
    echo 'Invalid Table Name<br>';
}

//echo 'Finished<br>';
?>
