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

$eventId = (int)$_GET['id'];

// Instanciamos la clase de acceso a datos
$dataAccess = new CalendarDataAccess(__DIR__ . "/../DesarrolloAplicacionPHP/data-access/calendar.db");

// Obtenemos el evento
$event = $dataAccess->getEventById($eventId);

// Verificamos que el evento existe y pertenece al usuario
if (!$event || $event->getUserId() !== $userId) {
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
    <div class="container mt-5">
        <h2 class="mb-3 text-center">¿Seguro que desea eliminar el evento "<?php echo $event->getTitle(); ?>"?</h2>
        <form method="post">
            <div class="d-flex justify-content-around">
                <button type="submit" name="action" value="yes" class="btn btn-danger">Sí, eliminar el evento</button>
                <button type="submit" name="action" value="no" class="btn btn-secondary">No, volver al listado de eventos</button>
            </div>
        </form>
    </div>
</body>

</html>