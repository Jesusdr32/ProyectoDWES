package es.iesclaradelrey.da2d1e.shopeahjdr.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AvailableUsernameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AvailableUsername {
    String message() default "El nombre de usuario no está disponible";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}