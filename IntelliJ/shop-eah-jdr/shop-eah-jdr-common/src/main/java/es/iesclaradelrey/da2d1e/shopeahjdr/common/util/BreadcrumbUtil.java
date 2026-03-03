package es.iesclaradelrey.da2d1e.shopeahjdr.common.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BreadcrumbUtil {

    public List<Map<String, String>> getBreadcrumb(String uri) {
        List<Map<String, String>> breadcrumb = new ArrayList<>();

        // Agregar siempre Inicio
        breadcrumb.add(Map.of("nombre", "Inicio", "url", "/"));

        // Dividir la URI en partes
        String[] partes = uri.split("/");
        String pathAcumulator = "";
        for (String parte : partes) {
            if (parte.isBlank()) continue;
            pathAcumulator += "/" + parte;
            breadcrumb.add(Map.of("nombre", capitalize(parte),
                    "url", pathAcumulator));
        }

        return breadcrumb;
    }

    private String capitalize(String str) {
        // Convierte "inicio" -> "Inicio"
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
