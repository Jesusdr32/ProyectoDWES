package es.iesclaradelrey.da2d1e.shopeahjdr.common.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = Ean13Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEan13 {
    String message() default "El codigo EAN-13 es obligatorio y debe tener 13 digitos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
