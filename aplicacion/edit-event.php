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
    <title>Schedulr-Editar evento</title>
    <link rel="icon" href="icono-aplicacion.svg" type="image/svg+xml">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
    <?php
    $evento = null;
    //Obtener el id del evento a editar
    $idEvento = filter_input(INPUT_GET, 'id', FILTER_VALIDATE_INT) ?? false;
    if ($idEvento !== false) {
        //Si el usuario tiene o no el evento 
        $evento = $dataAccess->getEventById($idEvento);
    }
    //Si el evento existe o no
    if ($evento == null || $evento->getUserId() != $_SESSION['user_id']) {
        echo '<div class="alert alert-danger text-center"><p>No se puede acceder al evento porque no existe o porque no tiene permisos para verlo.</p>
              <a href="events.php">Volver al listado de eventos</div>';
    } else {
        //Obtenemos los datos del evento en cuestión
        $errors = [];
        $title = $evento->getTitle() ?? '';
        $start_date = $evento->getStartDate() ?? '';
        $end_date = $evento->getEndDate() ?? '';
        $description = $evento->getDescription() ?? '';

        //Envio del formulario por el método POST
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $title = trim($_POST['title'] ?? '');
            $description = trim($_POST['description'] ?? '');
            $start_date = $_POST['start_date'] ?? '';
            $end_date = $_POST['end_date'] ?? '';

            // Validar campos obligatorios
            if ($title === '') {
                array_push($errors, 'El título es obligatorio.');
            }
            if ($start_date === '') {
                array_push($errors, 'La fecha y hora de inicio son obligatorias.');
            }
            if ($end_date === '') {
                array_push($errors, 'La fecha y hora de fin son obligatorias.');
            }

            // Validar formato y lógica de fechas y formateo de la fecha para que en events.php no aparezca la T
            if ($start_date && $end_date) {
                try {
                    $startDateTime = new DateTime($start_date);
                    $endDateTime = new DateTime($end_date);

                    if ($endDateTime <= $startDateTime) {
                        array_push($errors, 'La fecha y hora de fin debe ser posterior a la fecha y hora de inicio.');
                    } else {
                        $start_date = $startDateTime->format('Y-m-d H:i');
                        $end_date = $endDateTime->format('Y-m-d H:i');
                    }
                } catch (Exception $e) {
                    array_push($errors, 'Formato de fecha y hora inválido.');
                }
            }
        }

    ?>
        <!--Formulario de edición del evento con los campos rellenados con los datos antiguos-->
        <!--El evento ha sido modificado correctramente, ahora se manda al usuario a la página de events.php para mostrar los eventos modificados-->
        <?php if (($_SERVER['REQUEST_METHOD'] == 'POST')): ?>
            <?php if ($_POST['action'] === 'cancel') {
                header('Location: events.php');
                exit;
            }
            if (empty($errors) && $_POST['action'] === 'edit-event') {
                // Modificar el evento
                $event = new Event($_SESSION['user_id'], $title, $description, $start_date, $end_date, $idEvento);
                if ($dataAccess->updateEvent($event)) {
                    // Redirigir a lista de eventos
                    header('Location: events.php');
                    exit;
                } else {
                    array_push($errors, 'Error al guardar el evento. Intenta nuevamente.');
                }
            } ?>
        <?php endif; ?>
        <!--Enseña todos los errores del formulario-->
        <?php if ($_SERVER['REQUEST_METHOD'] == 'GET' || !empty($errors)): ?>
            <?php if (!empty($errors)): ?>
                <div class="alert alert-danger">
                    <ul class="list-unstyled text-center">
                        <?php foreach ($errors as $error): ?>
                            <li><?= $error ?></li>
                        <?php endforeach ?>
                    </ul>
                </div>
            <?php endif; ?>
            <div class="container d-flex justify-content-center align-items-center p-5" style="font-size: 1.4rem;">
                <div>
                    <form method="post" class="bg-white rounded shadow" style="padding: 3rem; width: 350px;">
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
                        <div class="mb-3 text-center">
                            <button type="submit" name="action" value="edit-event" class="btn btn-outline-primary btn-lg shadow-sm">Confirmar</button>
                            <button type="submit" name="action" value="cancel" formnovalidate class="btn btn-outline-danger btn-lg shadow-sm">Cancelar</button>
                        </div>
                    </form>
                </div>
            <?php endif; ?>
            </div>
        <?php
    }
        ?>
</body>

</html>