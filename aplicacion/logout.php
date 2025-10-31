<?php
session_start();

include __DIR__ . '/../aplicacion/cabecera.php';

if (!isset($_SESSION['user_id'])) {
    header("Location: index.php");
    exit;
}

if ($_SERVER["REQUEST_METHOD"] === 'POST') {
    if (isset($_POST['action']) && $_POST['action'] === 'logout') {
        session_unset();
        session_regenerate_id(true);
        session_destroy();
        setcookie(session_name(), '', time() - 3600, '/');
        header("Location: index.php");
        exit;
    } elseif (isset($_POST['action']) && $_POST['action'] === 'cancel') {
        header("Location: events.php");
        exit;
    }
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>

<body>
    <div class="d-flex justify-content-center align-items-center" style="min-height: 80vh;">
        <div class="card p-4 text-center shadow-lg" style="max-width: 400px; border-radius: 15px;">
            <div class="card-body">
                <h3 class="card-title mb-3"><i class="fas fa-sign-out-alt" style="font-size: 2rem; color: #dc3545;"></i><br>¿Deseas desconectarte?</h3>
                <p class="text-muted mb-4">Tu sesión se cerrará y tendrás que iniciar sesión nuevamente para acceder.</p>
                <form method="post">
                    <div class="d-flex justify-content-around">
                        <button type="submit" name="action" value="logout" class="btn btn-outline-danger btn-lg shadow-sm"><i class="fa-solid fa-power-off"></i><br>Sí, desconectar</button>
                        <button type="submit" name="action" value="cancel" class="btn btn-outline-secondary btn-lg shadow-sm"><i class="fas fa-arrow-left me-2"></i><br>No, volver</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>

</html>
<!-- Contenido principal de la página -->