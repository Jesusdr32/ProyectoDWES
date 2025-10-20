<?php
session_start();

//Incluir la clase cabecera.php
include __DIR__ . '../aplicacion/cabecera.php';

//Incluir la clase CalendarDataAccess.php
require_once __DIR__ . '../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';

//Ruta al archivo de base de datos SQLite
$dbFile = __DIR__ . '../DesarrolloAplicacionPHP/data-access/calendar.db';

//Crear instancia de acceso a datos
$dataAccess = new CalendarDataAccess($dbFile);

//Si no hay sesión iniciada, redirigir a index.php
if (!isset($_SESSION['user_id'])) {
    header('Location: index.php');
    exit;
}

//Si el usuario tiene o no eventos creados
