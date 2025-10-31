<?php
//Incluir la clase CalendarDataAccess
require_once __DIR__ . '/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';

//Ruta al archivo de base de datos SQLite
$dbFile = __DIR__ . '/../DesarrolloAplicacionPHP/data-access/calendar.db';

//Ruta a la página logout.php
$logout = '../aplicacion/logout.php';

//Ruta a la página change-password.php
$change_password = '#!';

//Ruta a la página user-profile.php
$user_profile = '/../aplicacion/profile.php';

//Crear instancia de acceso a datos
$dataAccess = new CalendarDataAccess($dbFile);

//Comprobamos si hay alguna sesión iniciada
$name = '';
$last_name = '';

if (isset($_SESSION['user_id'])) {
    $users = $dataAccess->getAllUsers();

    foreach ($users as $user) {
        if ($user->getId() === $_SESSION['user_id']) {
            $name = $user->getFirstName();
            $last_name = $user->getLastName();
            break;
        }
    }
}
?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="icono-aplicacion.svg" type="image/svg+xml">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <span class="navbar-brand"><a class="nav-link" href="/../aplicacion/events.php"><i class="fa-solid fa-calendar-days"></i>Schedulr</a></span>
            <?php if ($name): ?>
                <span class="navbar-text"><i class="fa-solid fa-user"></i><?= $name . ' ' . $last_name ?></span>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="<?= $user_profile ?>">Perfil de usuario</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<?= $change_password ?>">Cambiar contraseña</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<?= $logout ?>">Cerrar Sesión</a>
                    </li>
                <?php else: ?>
                    <li class="nav-item">
                        <span class="navbar-brand">Usuario no identificado</span>
                    </li>
                <?php endif; ?>
                </ul>
        </div>
    </nav>
</body>

</html>