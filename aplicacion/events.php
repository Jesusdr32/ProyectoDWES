<?php
session_start();

//Incluir la clase cabecera.php
include __DIR__ . '/../aplicacion/cabecera.php';

//Incluir la clase CalendarDataAccess
require_once __DIR__ . '/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';

//Ruta al archivo de base de datos SQLite
$dbFile = __DIR__ . '/../DesarrolloAplicacionPHP/data-access/calendar.db';

//Crear instancia de acceso a datos
$dataAccess = new CalendarDataAccess($dbFile);

//Si no hay sesión iniciada, redirigir a index.php
if (!isset($_SESSION['user_id'])) {
    header('Location: index.php');
    exit();
}

//Si el usuario tiene o no eventos creados
if (empty($dataAccess->getEventsByUserId($_SESSION['user_id']))) {
    $hayEventos = false;
} else {
    $hayEventos = true;
    $eventos = $dataAccess->getEventsByUserId($_SESSION['user_id']);
}

?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agenda de eventos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<body>
    <div class="container d-flex mt-5 justify-content-center align-items-center vh-100 p-5" style="font-size: 1.2rem;">
        <div>
            <h2 class="mb-4 text-center">Eventos</h2>
            <div class="container">
                <a href="../aplicacion/new-event.php">Nuevo evento</a>
                <?php if ($hayEventos): ?>
                    <?php foreach ($eventos as $evento): ?>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Titulo</th>
                                    <th>Descripcion</th>
                                    <th>Fecha y hora de inicio</th>
                                    <th>Fecha y hora de fin</th>
                                    <th>Modificaciones</th>
                                </tr>
                            </thead>
                            <tr>
                                <td><?= $evento->getId() ?></td>
                                <td><?= $evento->getTitle() ?></td>
                                <td><?= $evento->getDescription() ?></td>
                                <td><?= $evento->getStartDate() ?></td>
                                <td><?= $evento->getEndDate() ?></td>
                                <td><a href="../aplicacion/edit-event.php">Editar evento</a> / <a href="../aplicacion/delete-event.php">Eliminar evento</a></td>
                            </tr>

                        </table>
                    <?php endforeach ?>
                <?php else : ?>
                    <p>No hay eventos</p>
                <?php endif ?>
                <a href="../aplicacion/new-event.php">Nuevo evento</a>
            </div>
        </div>
    </div>
</body>

</html>