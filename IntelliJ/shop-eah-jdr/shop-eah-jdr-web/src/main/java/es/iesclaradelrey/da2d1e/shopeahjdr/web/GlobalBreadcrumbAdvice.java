package es.iesclaradelrey.da2d1e.shopeahjdr.web;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.util.BreadcrumbUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalBreadcrumbAdvice {

    @Autowired
    private BreadcrumbUtil breadcrumbUtil;

    // Esto se ejecuta antes de cada controlador
    @ModelAttribute("breadcrumb")
    public List<Map<String, String>> addBreadcrumb(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return breadcrumbUtil.getBreadcrumb(uri);
    }
}
