<?php
session_start();

include __DIR__ . '/../aplicacion/cabecera.php';

//Si no hay una sesión iniciada, la página se redirige de forma automática a index.php
if (!isset($_SESSION['user_id'])) {
    header("Location: index.php");
    exit;
}

//Comprobación del formulario enviado
if ($_SERVER["REQUEST_METHOD"] === 'POST') {
    if (isset($_POST['action']) && $_POST['action'] === 'logout') {
        //Cerrar sesión correctamente 
        session_unset();
        session_destroy();
        setcookie(session_name(), '', time() - 3600, '/'); //Borrar las cookies
        header("Location: index.php");
        exit;
    } elseif (isset($_POST['action']) && $_POST['action'] === 'cancel') {
        //No cerramos sesión, redirigimos a events.php
        header("Location: events.php");
        exit;
    }
}

?>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logout</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>

<body class="bg-light">
    <div class="container mt-5">
        <h3 class="mb-3 text-center">¿Seguro que desa desconectar?</h3>
        <form method="post">
            <div class="d-flex justify-content-around">
                <button type="submit" name="action" value="logout" class="btn btn-outline-danger">Sí, desconectar</button>
                <button type="submit" name="action" value="cancel" class="btn btn-outline-secondary">No, volver al listado de eventos</button>
            </div>
        </form>
    </div>
</body>

</html>