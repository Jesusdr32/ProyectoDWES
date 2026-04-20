package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.validation.AvailableUsername;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El nombre de usuario debe tener entre 4 y 50 caracteres")
    @AvailableUsername
    private String username;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String firstName;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 150, message = "Los apellidos deben tener entre 2 y 150 caracteres")
    private String lastName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 150, message = "El email no puede superar los 150 caracteres")
    private String email;

    @NotNull(message = "El teléfono es obligatorio")
    @Min(value = 100000000, message = "El teléfono debe tener 9 dígitos")
    @Max(value = 999999999, message = "El teléfono debe tener 9 dígitos")
    private Integer phone;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    @NotBlank(message = "Debe confirmar la contraseña")
    private String confirmPassword;

    @AssertTrue(message = "Debe aceptar las condiciones")
    private boolean acceptConditions;
}