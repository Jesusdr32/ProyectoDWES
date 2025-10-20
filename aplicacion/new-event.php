<?php
session_start();
require_once __DIR__ . '/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';


require_once '/../aplicacion/events.php';

// Rutas de la base de datos

$dbFile = __DIR__ . '/../DesarrolloAplicacionPHP/data-access/calendar.db';
$dataAccess = new CalendarDataAccess($dbFile);

// Simular usuario conectado
$userId = $_SESSION['user_id'] ?? null;
if (!$userId) {
    header('Location: login.php');
    exit;
}

$errors = [];
$title = '';
$description = '';
$startDate = '';
$endDate = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $title = trim($_POST['title'] ?? '');
    $description = trim($_POST['description'] ?? '');
    $startDate = $_POST['start_date'] ?? '';
    $endDate = $_POST['end_date'] ?? '';

    // Validar campos obligatorios
    if ($title === '') {
        $errors[] = 'El título es obligatorio.';
    }
    if ($startDate === '') {
        $errors[] = 'La fecha y hora de inicio son obligatorias.';
    }
    if ($endDate === '') {
        $errors[] = 'La fecha y hora de fin son obligatorias.';
    }

    // Validar formato y lógica de fechas
    if ($startDate && $endDate) {
        $startTimestamp = strtotime($startDate);
        $endTimestamp = strtotime($endDate);

        if ($startTimestamp === false || $endTimestamp === false) {
            $errors[] = 'Formato de fecha y hora inválido.';
        } elseif ($endTimestamp <= $startTimestamp) {
            $errors[] = 'La fecha y hora de fin debe ser posterior a la fecha y hora de inicio.';
        }
    }

    if (empty($errors)) {
        // Crear evento
        $event = new Event($userId, $title, $description, $startDate, $endDate);
        if ($dataAccess->createEvent($event)) {
            // Redirigir a lista de eventos
            header('Location: events_list.php');
            exit;
        } else {
            $errors[] = 'Error al guardar el evento. Intenta nuevamente.';
        }
    }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <title>Nuevo Evento</title>
</head>

<body>
    <div>
        <form action="#!" method="post">

            <label for="titulo">Título</label>
            <input type="text" name="titutlo" id="titulo" value="<?= $title ?>" required>

            <label for="descripcion">Descripción</label>
            <textarea name="descripcion" id="descripcion" value="<?= $description ?>"></textarea>

            <label for="fecha_hora_inicio">Fecha y hora de inicio</label>
            <input type="datetime-local" name="fecha_hora_inicio" id="fecha_hora_inicio" value="<?= $start_date ?>" required>

            <label for="fecha_hora_fin">Fecha y hora de fin</label>
            <input type="datetime-local" name="fecha_hora_fin" id="fecha_hora_fin" value="<?= $end_date ?>">

            <button type="submit">Crear un nuevo evento</button>
        </form>
    </div>
</body>

</html>