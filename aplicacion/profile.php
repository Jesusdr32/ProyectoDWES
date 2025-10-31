<?php
session_start();

//Incluir la clase cabecera.php
include __DIR__ . "/../aplicacion/cabecera.php";

//Incluir la clase CalendarDataAccess.php
require_once __DIR__ . "/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php";

//Ruta al archivo de base de datos SQLite
$dbFile = __DIR__ . "/../DesarrolloAplicacionPHP/data-access/calendar.db";

//Crear instancia de acceso a datos
$dataAccess = new CalendarDataAccess($dbFile);

//Si no hay sesión iniciada, redirigir a index.php
if (!isset($_SESSION['user_id'])) {
    header('Location: index.php');
    exit;
}

?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <title>Perfil del Usuario</title>
</head>

<body>
    <div class="justify-content-center align-items-center text-center p-5">
        <h2>Perfil de Usuario</h2>
        <dl>
            <dt>Correo electrónico:</dt>
            <dd><?= $user->getEmail() ?></dd>

            <dt>Nombre:</dt>
            <dd><?= $user->getFirstName() ?></dd>

            <dt>Apellidos:</dt>
            <dd><?= $user->getLastName() ?></dd>

            <dt>Fecha de nacimiento:</dt>
            <dd><?= $user->getBirthDate() ?></dd>

            <dt>Acerca de mí:</dt>
            <dd><?= $user->getAbout() ? nl2br(htmlspecialchars($user->getAbout())) : "Este elemento está vacío" ?></dd>
        </dl>
        <a href="#!">Modificar Perfil</a>
    </div>
</body>

</html>