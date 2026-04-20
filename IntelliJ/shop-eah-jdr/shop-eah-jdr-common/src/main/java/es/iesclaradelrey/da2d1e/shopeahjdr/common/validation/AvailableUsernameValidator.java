package es.iesclaradelrey.da2d1e.shopeahjdr.common.validation;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.AppUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class AvailableUsernameValidator implements ConstraintValidator<AvailableUsername, String> {
    private final AppUserService appUserService;
    public AvailableUsernameValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null || username.isBlank()) {
            return true;
        }
        return !appUserService.existsByUsername(username);
    }
}