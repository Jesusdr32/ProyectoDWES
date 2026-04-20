package es.iesclaradelrey.da2d1e.shopeahjdr.common.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Ean13Validator implements ConstraintValidator<ValidEan13, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        if (!value.matches("\\d{13}")) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(value.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int expectedCheckDigit = (10 - (sum % 10)) % 10;
        int actualCheckDigit = Character.getNumericValue(value.charAt(12));
        return expectedCheckDigit == actualCheckDigit;
    }
}
