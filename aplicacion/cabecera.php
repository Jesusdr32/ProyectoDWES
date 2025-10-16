<?php
session_start();

//Incluir la clase CalendarDataAccess
require_once __DIR__ . '../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';

//Ruta al archivo de base de datos SQLite
$dbFile = __DIR__ . '../DesarrolloAplicacionPHP/data-access/calendar.db';

//Ruta a la página logout.php
$logout = __DIR__ . '../aplicacion/logout.php';

//Ruta a la página change-password.php
$change_password = '#!';

//Ruta a la página user-profile.php
$user_profile = '#!';

//Crear instancia de acceso a datos
$dataAccess = new CalendarDataAccess($dbFile);

//Obtener todos los usuarios
$users = $dataAccess->getAllUsers();

//Ver que usuario está conectado a la página
foreach ($users as $user) {
    if ($user->getId() === $_SESSION['user_id']) {
        $name = $user->getFirstName();
        $last_name = $user->getLastName();
    }
}
?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cabecera</title>
</head>

<body>

</body>

</html>