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

@Controller
@RequestMapping("/users")
public class UserController {

    private final AppUserService appUserService;


    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/users/profile")
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
    @GetMapping("/users/profile/{userId}")
    public String profileById(@PathVariable Long userId, Model model) {
        AppUser user = appUserService.findById(userId)
                .orElseThrow();

        model.addAttribute("user", user);
        return "users/profile";
    }
}