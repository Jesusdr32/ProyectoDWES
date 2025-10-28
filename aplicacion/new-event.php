<?php
session_start();

require_once __DIR__ . '/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';

include __DIR__ . '/../aplicacion/cabecera.php';

// Rutas de la base de datos

$dbFile = __DIR__ . '/../DesarrolloAplicacionPHP/data-access/calendar.db';
$dataAccess = new CalendarDataAccess($dbFile);

$errors = [];
$title = '';
$description = '';
$start_date = '';
$end_date = '';

//Envio del formulario por el método POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $title = trim($_POST['title'] ?? '');
    $description = trim($_POST['description'] ?? '');
    $start_date = $_POST['start_date'] ?? '';
    $end_date = $_POST['end_date'] ?? '';

    // Validar campos obligatorios
    if ($title === '') {
        $errors[] = 'El título es obligatorio.';
    }
    if ($start_date === '') {
        $errors[] = 'La fecha y hora de inicio son obligatorias.';
    }
    if ($end_date === '') {
        $errors[] = 'La fecha y hora de fin son obligatorias.';
    }

    // Validar formato y lógica de fechas y formateo de la fecha para que en events.php no aparezca la T
    if ($start_date && $end_date) {
        try {
            $startDateTime = new DateTime($start_date);
            $endDateTime = new DateTime($end_date);

            if ($endDateTime <= $startDateTime) {
                $errors[] = 'La fecha y hora de fin debe ser posterior a la fecha y hora de inicio.';
            } else {
                $start_date = $startDateTime->format('Y-m-d H:i');
                $end_date = $endDateTime->format('Y-m-d H:i');
            }
        } catch (Exception $e) {
            $errors[] = 'Formato de fecha y hora inválido.';
        }
    }

    if (empty($errors)) {
        // Crear evento
        $event = new Event($userId, $title, $description, $start_date, $end_date);
        if ($dataAccess->createEvent($event)) {
            // Redirigir a lista de eventos
            header('Location: events.php');
            exit;
        } else {
            array_push($errors, "Error al guardar el evento. Intenta nuevamente.");
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
    <div class="container d-flex justify-content-center align-items-center p-5" style="font-size: 1.4rem;">
        <form method="post">
            <div class="mb-3 text-center">
                <label for="title" class="form-label">Título</label>
                <input type="text" class="form-control" name="title" id="title" value="<?= $title ?>" required>
            </div>
            <div class="mb-3 text-center">
                <label for="fecha_hora_inicio" class="form-label">Fecha y hora de inicio</label>
                <input type="datetime-local" name="start_date" id="start_date" class="form-control" value="<?= $start_date ?>" required>
            </div>
            <div class="mb-3 text-center">
                <label for="fecha_hora_fin" class="form-label">Fecha y hora de fin</label>
                <input type="datetime-local" class="form-control" name="end_date" id="end_date" value="<?= $end_date ?>" required>
            </div>
            <div class="mb-3 text-center ">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea name="description" class="form-control" id="description"><?= $description ?></textarea>
            </div>
            <div class="mb-3">
                <button type="submit" name="action" value="new-event" class="btn btn-primary w-100">Crear un nuevo evento</button>
            </div>
        </form>
    </div>
</body>

</html>