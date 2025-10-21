<?php
session_start();


// Incluir la clase CalendarDataAccess
require_once __DIR__ . '/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';


// Ruta al archivo de base de datos SQLite
$dbFile = __DIR__ . '/../DesarrolloAplicacionPHP/data-access/calendar.db';


// Crear instancia de acceso a datos    
$dataAccess = new CalendarDataAccess($dbFile);


// Si ya hay sesión iniciada, redirigir a events.php
if (isset($_SESSION['user_id'])) {
    header('Location: events.php');
    exit();
}


// Aquí seguiría la lógica para procesar el formulario de login, mostrar errores, etc.

$error = '';
$email = '';
$password = '';
$remember = false;

// Verificar si viene cookie con user id para rellenar el email
if (isset($_COOKIE['remember_user_id'])) {
    $userFromCookie = $dataAccess->getUserById((int)$_COOKIE['remember_user_id']);
    if ($userFromCookie) {
        $email = $userFromCookie->getEmail();
    }
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $email = trim($_POST['email'] ?? '');
    $password = $_POST['password'] ?? '';
    $remember = isset($_POST['remember']);

    // Validar email y contraseña obligatorios
    if (empty($email)) {
        $error = 'El correo electrónico es obligatorio.';
    } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $error = 'Formato de correo electrónico no válido.';
    } elseif (empty($password)) {
        $error = 'La contraseña es obligatoria.';
    } else {
        // Buscar usuario por email
        $user = $dataAccess->getUserByEmail($email);
        if (!$user || !password_verify($password, $user->getPassword())) {
            $error = 'Correo electrónico o contraseña incorrectos.';
        } else {
            // Login exitoso
            $_SESSION['user_id'] = $user->getId();

            // Guardar cookie si recuerda usuario
            if ($remember) {
                setcookie('remember_user_id', $user->getId(), time() + 120, "/"); // 2 minutos
            } else {
                setcookie('remember_user_id', '', time() - 3600, "/"); // borrar cookie si existe
            }

            header('Location: events.php');
            exit;
        }
    }
}

?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8" />
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
    <div class="container d-flex justify-content-center align-items-center vh-100 p-5" style="font-size: 1.2rem;">
        <div>
            <h2 class="mb-4 text-center">Iniciar sesión</h2>

            <?php if (!empty($error)): ?>
                <div class="alert alert-danger"><?= ($error) ?></div>
            <?php endif; ?>

            <form method="POST" action="">
                <div class="mb-3">
                    <label for="email" class="form-label">Correo electrónico</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="usuario@ejemplo.com" required value="<?= ($email) ?>" />
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="········" required />
                </div>

                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="remember" name="remember" <?= $remember ? 'checked' : '' ?> />
                    <label class="form-check-label" for="remember">Recuérdame</label>
                </div>

                <button type="submit" name="action" value="login" class="btn btn-primary w-100">Entrar</button>
            </form>

            <p class="mt-3">¿No tienes cuenta? <a href="register.php">Regístrate</a></p>
        </div>
    </div>
</body>

</html>