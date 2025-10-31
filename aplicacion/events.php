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
    <title>Schedulr-Agenda de eventos</title>
    <link rel="icon" href="icono-aplicacion.svg" type="image/svg+xml">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<body>
    <div class="container d-flex justify-content-center align-items-center p-5" style="font-size: 1.2rem;">
        <div>
            <h2 class="mb-4 text-center">Eventos</h2>
            <div class="container">
                <a href="../aplicacion/new-event.php"><i class="fa-regular fa-calendar-plus"></i><span class="visually-hidden">Nuevo evento</span></a>
                <?php if ($hayEventos): ?>
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr class="table-primary">
                                <th scope="col">Título</th>
                                <th scope="col">Descripción</th>
                                <th scope="col">Fecha y hora de inicio</th>
                                <th scope="col">Fecha y hora de fin</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <?php foreach ($eventos as $evento): ?>
                            <tr>
                                <td><?= $evento->getTitle() ?></td>
                                <td class="text-truncate" style="max-width: 20em;"><?= $evento->getDescription() ?></td>
                                <td><?= $evento->getStartDate() ?></td>
                                <td><?= $evento->getEndDate() ?></td>
                                <td><a href="../aplicacion/edit-event.php?id=<?= $evento->getId() ?>"><i class="fa-regular fa-pen-to-square"></i><span class="visually-hidden">Editar evento</span></a> / <a href="../aplicacion/delete-event.php?id=<?= $evento->getId() ?>"><i class="fa-regular fa-trash-can"></i><span class="visually-hidden">Eliminar evento</span></a></td>
                            </tr>
                        <?php endforeach ?>
                    </table>
                    <a href="../aplicacion/new-event.php"><i class="fa-regular fa-calendar-plus"></i><span class="visually-hidden">Nuevo evento</span></a>
                <?php else : ?>
                    <div class="alert alert-warning">
                        <p class="text-center">No hay eventos</p>
                    </div>
                <?php endif ?>
            </div>
        </div>
    </div>
</body>

</html>