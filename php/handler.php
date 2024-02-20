<?php

$action = $_GET['action'] ?? '';

switch ($action) 
{
    case 'db_manager':
        require 'db_manager.php';
        break;
    case 'get_table':
        require 'get_table.php';
        break;
    case 'update_table':
        require 'update_table.php';
        break;
    default:
        echo 'Hello World! Bu bir test mesajıdır.';
        break;
}
