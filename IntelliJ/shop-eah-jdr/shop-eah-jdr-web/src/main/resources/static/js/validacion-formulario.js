
    document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("categoryForm");

    const nameInput = document.getElementById("name");
    const descriptionInput = document.getElementById("description");
    const imageInput = document.getElementById("image");

    function setError(input, errorId, message) {
    input.classList.add("is-invalid");
    input.classList.remove("is-valid");
    document.getElementById(errorId).textContent = message;
}

    function setSuccess(input, errorId) {
    input.classList.remove("is-invalid");
    input.classList.add("is-valid");
    document.getElementById(errorId).textContent = "";
}

    function validateName() {
    const value = nameInput.value.trim();

    if (value === "") {
    setError(nameInput, "nameError", "El nombre es obligatorio.");
    return false;
}

    if (value.length < 3) {
    setError(nameInput, "nameError", "El nombre debe tener al menos 3 caracteres.");
    return false;
}

    setSuccess(nameInput, "nameError");
    return true;
}

    function validateDescription() {
    const value = descriptionInput.value.trim();

    if (value === "") {
    setError(descriptionInput, "descriptionError", "La descripción es obligatoria.");
    return false;
}

    if (value.length < 5) {
    setError(descriptionInput, "descriptionError", "La descripción debe tener al menos 5 caracteres.");
    return false;
}

    setSuccess(descriptionInput, "descriptionError");
    return true;
}

    function validateImage() {
    const value = imageInput.value.trim();

    if (value === "") {
    setError(imageInput, "imageError", "La URL de la imagen es obligatoria.");
    return false;
}

    try {
    new URL(value);
} catch (e) {
    setError(imageInput, "imageError", "Introduce una URL válida.");
    return false;
}

    setSuccess(imageInput, "imageError");
    return true;
}

    nameInput.addEventListener("blur", validateName);
    descriptionInput.addEventListener("blur", validateDescription);
    imageInput.addEventListener("blur", validateImage);

    form.addEventListener("submit", function (event) {
    const validName = validateName();
    const validDescription = validateDescription();
    const validImage = validateImage();

    if (!validName || !validDescription || !validImage) {
    event.preventDefault();
}
});
});