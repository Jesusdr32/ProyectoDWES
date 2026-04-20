package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Value("${shop.validation.client-validation-enabled:true}")
    private boolean clientValidationEnabled;

    @ModelAttribute("clientValidationEnabled")
    public boolean getClientValidationEnabled() {
        return clientValidationEnabled;
    }
}
