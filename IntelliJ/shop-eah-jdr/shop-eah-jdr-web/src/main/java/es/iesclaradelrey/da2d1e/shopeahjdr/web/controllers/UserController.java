package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.AppUserService;
import es.iesclaradelrey.da2d1e.shopeahjdr.security.AppUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {

    private final AppUserService appUserService;


    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        AppUser user = appUserService.findByUsernameIgnoreCase(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("user", user);
        model.addAttribute("title", "GEX - Profile of " + user.getUsername());
        model.addAttribute("subtitulo", "Datos del usuario :P");
        model.addAttribute("titulo", "Perfil de  " + user.getUsername());
        return "users/profile";
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    @GetMapping("/profile/{userId}")
    public String profileById(@PathVariable Long userId, Model model) {
        AppUser user = appUserService.findById(userId)
                .orElseThrow();

        model.addAttribute("user", user);
        model.addAttribute("title", "GEX - Profile of " + user.getUsername());
        model.addAttribute("subtitulo", "Datos del usuario :P");
        model.addAttribute("titulo", "Perfil de  " + user.getUsername());
        return "users/profile";
    }

    @GetMapping("/login")
    public String login(Authentication authentication,
                        @RequestParam(value = "error", required = false) String error,
                        Model model) {
        model.addAttribute("title", "GEX - Login");

        // Si el usuario ya está utenticado, redirigir a inicio
        if (authentication != null
        && authentication.isAuthenticated()
        && !(authentication.getPrincipal() instanceof String)) {
            return "redirect:/";
        }

        // Mostrar mensaje de error si el login falla
        if (error != null) {
            model.addAttribute("error", "Usuario y/o contraseña incorrectos");
        }

        // Mostrar la página login
        return "users/login";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model) {
        model.addAttribute("title", "GEX - Cerrar sesión");
        return "users/logout";
    }
}