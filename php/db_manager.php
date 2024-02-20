<?php

function connectDatabase() 
{
    $conn = mysqli_connect(null, "root", "123456bbbB", "diyetisyen", "0", "/cloudsql/bitirmeprojesi-firebase:europe-west9:bitirme");

    if (!$conn) 
    {
        die("Connection failed: " . mysqli_connect_error());
    }

    return $conn;
}

function getAllData($tableName) 
{
    $conn = connectDatabase();

    $sql = "SELECT * FROM $tableName";
    $result = mysqli_query($conn, $sql);

    $data = [];

    if (mysqli_num_rows($result) > 0) 
    {
        while ($row = mysqli_fetch_assoc($result)) 
        {
            $data[] = $row;
        }
    }

    mysqli_close($conn);
    return $data;
}

function getDataById($tableName, $tableIds, $id) 
{
    $conn = connectDatabase();      

    $sql = "SELECT * FROM $tableName WHERE $tableIds[$tableName] = $id";
    $result = mysqli_query($conn, $sql);

    $data = null;

    if (mysqli_num_rows($result) > 0) 
    {
        $data = mysqli_fetch_assoc($result);
    }

    mysqli_close($conn);
    return $data;
}


function insertData($tableName, $data)
 {
    $conn = connectDatabase();

    $fields = implode(", ", array_keys($data));
    $values = "'" . implode("', '", array_values($data)) . "'";

    $sql = "INSERT INTO $tableName ($fields) VALUES ($values)";

    if (mysqli_query($conn, $sql)) 
    {
        echo json_encode(['success' => 'Data inserted successfully']);
    } else 
    {
        echo json_encode(['error' => 'Error inserting data: ' . mysqli_error($conn)]);
    }

    mysqli_close($conn);
}


function updateData($tableName, $tableIds, $id, $data)
{
    $conn = connectDatabase();

    $setValues = [];
    foreach ($data as $key => $value) 
    {
        $setValues[] = "$key = '$value'";
    }

    $setValuesStr = implode(", ", $setValues);
    $sql = "UPDATE $tableName SET $setValuesStr WHERE $tableIds[$tableName] = $id";

    if (mysqli_query($conn, $sql)) 
    {
        echo json_encode(['success' => 'Data updated successfully']);
    } 
    else 
    {
        echo json_encode(['error' => 'Error updating data: ' . mysqli_error($conn)]);
    }

    mysqli_close($conn);
}


function deleteData($tableName, $tableIds, $id)
{
    $conn = connectDatabase();

    $sql = "DELETE FROM $tableName WHERE $tableIds[$tableName] = $id";

    if (mysqli_query($conn, $sql)) 
    {
        echo json_encode(['success' => 'Data deleted successfully']);
    } 
    else 
    {
        echo json_encode(['error' => 'Error deleting data: ' . mysqli_error($conn)]);
    }
    mysqli_close($conn);
}


$allowedTables = ['Allergen', 'ComponentAllergen', 'DietPlan', 'DietPlanDetail',
 'DietPreference', 'FoodComponent', 'FoodPreference', 'Goal', 'HealthCondition', 
 'Meal', 'MealComponent', 'Region', 'User', 'UserAllergy', 'UserMeal'];

$tableIds = [
    'Allergen' => 'AllergenID',
    'ComponentAllergen' => 'ComponentID',
    'DietPlan' => 'DietPlanID',
    'DietPlanDetail' => 'DetailID',
    'DietPreference' => 'DietPreferenceID',
    'FoodComponent' => 'ComponentID',
    'FoodPreference' => 'PreferenceID',
    'Goal' => 'GoalID',
    'HealthCondition' => 'ConditionID',
    'Meal' => 'MealID',
    'MealComponent' => 'MealComponentID',
    'Region' => 'RegionID',
    'User' => 'UserID',
    'UserAllergy' => 'UserID?', // PROBLEM HERE !!!
    'UserMeal' => 'UserMealID'
];

$tableName = isset($_GET['table_name']) ? $_GET['table_name'] : '';


if (in_array($tableName, $allowedTables)) 
{
    $operation = isset($_GET['operation']) ? $_GET['operation'] : '';

    switch ($operation) 
    {
        case 'get_all':
            $data = getAllData($tableName);
            echo json_encode($data);
            break;
        case 'get_by_id':
            $id = isset($_GET['id']) ? $_GET['id'] : '';

            if ($id !== '') 
            {
                $data = getDataById($tableName, $tableIds, $id);
                echo json_encode($data);
            }
            else
            {
                echo json_encode(['error' => 'ID not provided']);
            }
            break;
        case 'insert':
            $postData = json_decode(file_get_contents("php://input"), true);

            if (!empty($postData)) 
            {
                insertData($tableName, $postData);
            } 
            else 
            {
                echo json_encode(['error' => 'No data provided for insertion']);
            }
            break;
        case 'update':
            $id = isset($_GET['id']) ? $_GET['id'] : '';
            $postData = json_decode(file_get_contents("php://input"), true);

            if (!empty($postData) && $id !== '') 
            {
                updateData($tableName, $tableIds, $id, $postData);
            } 
            else 
            {
                echo json_encode(['error' => 'No data or ID provided for update']);
            }
            break;
        case 'delete':
            $id = isset($_GET['id']) ? $_GET['id'] : '';

            if ($id !== '') 
            {
                deleteData($tableName, $tableIds, $id);
            }
            else 
            {
                echo json_encode(['error' => 'ID not provided for deletion']);
            }
            break;
        default:
            echo json_encode(['error' => 'Invalid operation']);
            break;
    }
} 
else
{
    echo json_encode(['error' => 'Invalid table name']);
}

?>
