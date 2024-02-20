<?php
require 'config.php';

$tableName = $_POST['table_name'] ?? '';
$recordId = $_POST['record_id'] ?? '';

$updates = json_decode($_POST['updates'], true);


$allowedTables = ['table1', 'table2', 'table3'];
$allowedFields = ['username', 'email', 'age'];


$setParts = [];

foreach ($updates as $field => $value) 
{
    if (in_array($field, $allowedFields)) 
    {
        $setParts[] = "`$field` = '" . mysqli_real_escape_string($conn, $value) . "'";
    }
}
$setClause = implode(', ', $setParts);

// İzin verilen bir tablo adıysa ve güncellenecek alanlar varsa
if (in_array($tableName, $allowedTables) && !empty($setClause)) 
{
    // Veritabanına bağlan
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
    if ($conn->connect_error) 
    {
        die("Connection Error " . $conn->connect_error);
    }

    // SQL sorgusu
    $sql = "UPDATE `$tableName` SET $setClause WHERE id = $recordId";

    // Sorguyu çalıştır ve sonucu kontrol et
    if ($conn->query($sql) === TRUE) 
    {
        echo "SQL updated";
    }
    else
    {
        echo "Error: " . $conn->error;
    }

    // Veritabanı bağlantısını kapat
    $conn->close();
} 
else 
{
    echo "Invalid Table Name";
}
?>
