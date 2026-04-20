package es.iesclaradelrey.da2d1e.shopeahjdr.common.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueProductNameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueProductName {
    String message() default "Ya existe un producto con ese nombre";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
