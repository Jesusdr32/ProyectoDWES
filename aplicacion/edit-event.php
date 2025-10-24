<?php
session_start();

//Incluir la clase cabecera.php
include __DIR__ . '/../aplicacion/cabecera.php';

//Incluir la clase CalendarDataAccess.php
require_once __DIR__ . '/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';

//Ruta al archivo de base de datos SQLite
$dbFile = __DIR__ . '/../DesarrolloAplicacionPHP/data-access/calendar.db';

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
    <title>Editar evento</title>
</head>

<body>
    <?php
    //Obtener el id del evento a editar
    $idEvento = (int)$_GET['id'];

    //Si el usuario tiene o no el evento 
    $evento = $dataAccess->getEventById($idEvento);

    if ($evento == null || $evento->getUserId() != $_SESSION['user_id']) {
        echo '<div class="alert alert-danger text-center"><p>No se puede acceder al evento porque no existe o porque no tiene permisos para verlo.</p>
              <a href="events.php">Volver al listado de eventos</div>';
    } else {
        //Obtenemos los datos del evento en cuestión
        $tituloEvento = $evento->getTitle();
        $inicioEvento = $evento->getStartDate();
        $finEvento = $evento->getEndDate();
        $descripcionEvento = $evento->getDescription();
    ?>
        <div class="container d-flex justify-content-center align-items-center p-5" style="font-size: 1.4rem;">
            <!--Formulario de edición del evento con los campos rellenados con los datos antiguos-->
            <form method="post">
                <div class="mb-3 text-center">
                    <label for="title" class="form-label">Título</label>
                    <input type="text" class="form-control" name="title" id="title" value="<?= $tituloEvento ?>" required>
                </div>
                <div class="mb-3 text-center">
                    <label for="fecha_hora_inicio" class="form-label">Fecha y hora de inicio</label>
                    <input type="datetime-local" name="start_date" id="start_date" class="form-control" value="<?= $inicioEvento ?>" required>
                </div>
                <div class="mb-3 text-center">
                    <label for="fecha_hora_fin" class="form-label">Fecha y hora de fin</label>
                    <input type="datetime-local" class="form-control" name="end_date" id="end_date" value="<?= $finEvento ?>" required>
                </div>
                <div class="mb-3 text-center ">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <textarea name="description" class="form-control" id="description"><?= $descripcionEvento ?></textarea>
                </div>
                <div class="mb-3">
                    <button type="submit" name="action" value="edit-event" class="btn btn-primary">Confirmar editar evento</button>
                    <button type="submit" name="action" value="cancel" class="btn btn-danger">Cancelar editar evento</button>
                </div>
            </form>
        </div>
    <?php
    }
    ?>
</body>

</html>