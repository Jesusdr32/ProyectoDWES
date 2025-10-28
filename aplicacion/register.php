<?php
session_start();

//Inlcuir la clase User.php
require_once __DIR__ . '/../DesarrolloAplicacionPHP/entities/User.php';

//Inlcuir la clase DataAccess.php
require_once __DIR__ . '/../DesarrolloAplicacionPHP/data-access/CalendarDataAccess.php';

//Ruta de archivo de la base de datos SQLite
$dbFile = __DIR__ . '/../DesarrolloAplicacionPHP/data-access/calendar.db';

//Crear un objeto para acceso a la BD (abrir la base de datos)
$dataAccess = new CalendarDataAccess($dbFile);

//Si hay una sesión iniciada, redirigir a events.php
if (isset($_SESSION['userId'])) {
    header("Location: events.php");
    exit;
}

//Crear los atributos utilizados en la página
$errors = [];
$users = $dataAccess->getAllUsers();

$email = filter_input(INPUT_POST, 'email', FILTER_SANITIZE_FULL_SPECIAL_CHARS) ?? '';
$name = filter_input(INPUT_POST, 'name', FILTER_SANITIZE_FULL_SPECIAL_CHARS) ?? '';
$last_name = filter_input(INPUT_POST, 'last-name', FILTER_SANITIZE_FULL_SPECIAL_CHARS) ?? '';
$birth_day = filter_input(INPUT_POST, 'birth-day', FILTER_SANITIZE_FULL_SPECIAL_CHARS) ?? '';
$password = filter_input(INPUT_POST, 'password', FILTER_SANITIZE_FULL_SPECIAL_CHARS) ?? '';
$password_repeat = filter_input(INPUT_POST, 'password-repeat', FILTER_SANITIZE_FULL_SPECIAL_CHARS) ?? '';

//Validar todos los campos cuando son mandados por el método POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    //Validar el email
    if (empty($email)) {
        array_push($errors, "No se ha introducido el email del usuario");
    } else {
        $validatedEmail = filter_var($email, FILTER_VALIDATE_EMAIL);
        if ($validatedEmail === false) {
            array_push($errors, "El email introducido {$email} no es válido");
        }
    }

    //Validar el nombre
    if (empty($name)) {
        array_push($errors, "No se ha introducido el nombre del usuario");
    }

    //Validar los apellidos
    if (empty($last_name)) {
        array_push($errors, "No se han introducido los apellidos del usuario");
    }

    //Validar la fecha de nacimiento
    if (empty($birth_day)) {
        array_push($errors, "No se ha introducido la fecha de nacimiento del usuario");
    } else {
        $validatedBirthDay = DateTime::createFromFormat('Y-m-d', $birth_day);
        if ($validatedBirthDay === false) {
            array_push($errors, "La fecha de nacimiento introducida {$birth_day} no es válida");
        }
    }

    //Validar la contraseña
    if (empty($password)) {
        array_push($errors, "No se ha introducido la contraseña del usuario");
    } else {
        $patron = '/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/';
        $validatedPassword = filter_var($password, FILTER_VALIDATE_REGEXP, ['options' => ['regexp' => $patron]]);
        if ($validatedPassword === false) {
            array_push($errors, "La contraseña introducida {$password} no es válida");
        }
    }

    //Validar la repetición de contraseña
    if (empty($password_repeat)) {
        array_push($errors, "no se ha introducido la repetición de contraseña del usuario");
    } elseif ($password_repeat !== $password) {
        array_push($errors, "La repetición de contraseña no coincide con la contraseña");
    } else {
        $patron = '/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/';
        $validatedPasswordRepeat = filter_var($password_repeat, FILTER_VALIDATE_REGEXP, ['options' => ['regexp' => $patron]]);
        if ($validatedPasswordRepeat === false) {
            array_push($errors, "La repetición de contraseña introducida {$password_repeat} no es válida");
        }
    }

    //Validar que el correo del usuario introducido no exista en la BD
    foreach ($users as $user) {
        if ($email === $user->getEmail()) {
            array_push($errors, "El email introducido está sincronizado a un usuario existente");
        }
    }

    //Creación del nuevo usuario solo si no hay errores
    if (empty($errors)) {
        $newUser = new User($email, password_hash($password, PASSWORD_DEFAULT), $name, $last_name, $birth_day, null);
        if (!$dataAccess->createUser($newUser)) {
            array_push($errors, "Error al crear el usuario");
        }
    }
}

?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de registro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>

<body>
    <div class="container mt-5" style="max-width: 400px;">
        <h2 class="mb-4 text-center">Formulario de registro</h2>
        <!--El usuario ya ha sido creado, ahora se manda al usuario a la página de index.php para que inicie sesión-->
        <?php if (($_SERVER['REQUEST_METHOD'] == 'POST') && empty($errors)) : ?>
            <div class="alert alert-success">
                <p>Usuario creado con éxito</p><br>
                <a href="index.php">Inicia sesión</a>
            </div>
        <?php endif; ?>
        <?php if ($_SERVER['REQUEST_METHOD'] == 'GET' || !empty($errors)): ?>
            <!--Enseña todos los errores del formulario-->
            <?php if (!empty($errors)): ?>
                <div class="alert alert-danger">
                    <ul>
                        <?php foreach ($errors as $error): ?>
                            <li><?= $error ?></li>
                        <?php endforeach ?>
                    </ul>
                </div>
            <?php endif; ?>
            <form method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">Correo electrónico: </label>
                    <input type="email" name="email" id="email" class="form-control" placeholder="usuario@ejemplo.com" value="<?= htmlspecialchars($email) ?>" required>
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label">Nombre: </label>
                    <input type="text" name="name" id="name" class="form-control" placeholder="Nombre" value="<?= htmlspecialchars($name) ?>" required>
                </div>
                <div class="mb-3">
                    <label for="last-name" class="form-label">Apellidos: </label>
                    <input type="text" name="last-name" id="last-name" class="form-control" placeholder="Apellidos" value="<?= htmlspecialchars($last_name) ?>" required>
                </div>
                <div class="mb-3">
                    <label for="birth-day" class="form-label">Fecha de nacimiento: </label>
                    <input type="date" name="birth-day" id="birth-day" class="form-control" value="<?= htmlspecialchars($birth_day) ?>" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña: </label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="········" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$" title="La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra y un número" value="<?= htmlspecialchars($password) ?>" required>
                </div>
                <div class="mb-3">
                    <label for="password-repeat" class="form-label">Repetir contraseña: </label>
                    <input type="password" name="password-repeat" id="password-repeat" class="form-control" placeholder="········" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$" title="La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra y un número" value="<?= htmlspecialchars($password_repeat) ?>" required>
                </div>
                <div class="mb-3 d-flex justify-content-center">
                    <button type="submit" class="btn btn-primary">Registrarme</button>
                </div>
            </form>
            <div class="mb-3">
                <p>¿Tienes una cuenta ya? <a href="index.php">Iniciar sesión</a></p>
            </div>
        <?php endif; ?>
    </div>
</body>

</html>