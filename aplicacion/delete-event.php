<?php
session_start();

require_once __DIR__ . '/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';

include __DIR__ . '/../aplicacion/cabecera.php';

$dbFile = __DIR__ . '/../DesarrolloAplicacionPHP/data-access/calendar.db';
$dataAccess = new CalendarDataAccess($dbFile);

// Comprobamos que el usuario está logueado
if (!isset($_SESSION['user_id'])) {
    header("Location: login.php");
    exit;
}

$userId = $_SESSION['user_id'];

$eventId = filter_input(INPUT_GET, 'id', FILTER_VALIDATE_INT) ?? false;

// Instanciamos la clase de acceso a datos
$dataAccess = new CalendarDataAccess(__DIR__ . "/../DesarrolloAplicacionPHP/data-access/calendar.db");

// Obtenemos el evento
if ($eventId !== false) {
    $event = $dataAccess->getEventById($eventId);
}

// Verificamos que el evento existe y pertenece al usuario
if ($event == null || $event->getUserId() !== $userId) {
    echo '<div class="alert alert-danger text-center"><p>No se puede acceder al evento porque no existe o porque no tiene permisos para verlo.</p>
          <a href="events.php">Volver al listado de eventos</a></div>';
    exit;
}

// Procesamos el formulario
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['action'])) {
    if ($_POST['action'] === 'yes') {
        // Redirigimos a events despues de borrar
        $dataAccess->deleteEvent($eventId);
        header("Location: events.php");
        exit;
    } else {
        // Para redirigir si no se ha eliminado
        header("Location: events.php");
        exit;
    }
}

?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Eliminar evento</title>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center" style="min-height: 80vh;">
        <div class="card p-4 text-center shadow-lg" style="max-width: 400px; border-radius: 15px;">
            <div class="card-body">
                <h3 class="card-title mb-3">¿Seguro que desea eliminar el evento "<?php echo $event->getTitle(); ?>"?</h3>
                <form method="post">
                    <div class="d-flex justify-content-around">
                        <button type="submit" name="action" value="yes" class="btn btn-outline-danger"><i class="fa-regular fa-trash-can"></i><br>Sí, eliminar el evento</button>
                        <button type="submit" name="action" value="no" class="btn btn-outline-secondary"><i class="fas fa-arrow-left me-2"></i><br>No, volver al listado de eventos</button>
                </form>
            </div>
        </div>
    </div>
</body>

</html>