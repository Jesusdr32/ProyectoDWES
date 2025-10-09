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
        <form method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Correo electrónico: </label>
                <input type="email" name="email" id="email" class="form-control" placeholder="usuario@ejemplo.com" required>
            </div>
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre: </label>
                <input type="text" name="nombre" id="nombre" class="form-control" placeholder="Nombre usuario" required>
            </div>
            <div class="mb-3">
                <label for="apellidos" class="form-label">Apellidos: </label>
                <input type="text" name="apellidos" id="apellidos" class="form-control" placeholder="Apellidos usuario" required>
            </div>
            <div class="mb-3">
                <label for="fecha-nacimiento" class="form-label">Fecha de nacimiento: </label>
                <input type="date" name="fecha-nacimiento" id="fecha-nacimiento" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="contraseña" class="form-label">Contraseña: </label>
                <input type="password" name="contraseña" id="contraseña" class="form-control" minlength="8" required>
            </div>
            <div class="mb-3">
                <button type="submit" name="action" value="logout" class="btn btn-primary">Sí, desconectar</button>
            </div>
        </form>
    </div>
</body>

</html>