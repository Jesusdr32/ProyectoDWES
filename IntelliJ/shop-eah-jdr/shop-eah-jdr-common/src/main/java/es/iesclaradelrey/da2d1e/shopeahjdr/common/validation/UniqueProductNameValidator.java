package es.iesclaradelrey.da2d1e.shopeahjdr.common.validation;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    private final ProductService productService;
    public UniqueProductNameValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean isValid(String productName, ConstraintValidatorContext constraintValidatorContext) {
        if (productName == null || productName.isBlank()) {
            return true;
        }
        return !productService.existsByProductName(productName);
    }
}