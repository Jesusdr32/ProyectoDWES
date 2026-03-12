package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewUserDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.UserMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.AppUserService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class RegisterController {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public RegisterController(AppUserService appUserService, PasswordEncoder passwordEncoder, CategoryService categoryService, BrandService brandService) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping("/register")
    public ModelAndView registerForm(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/register");
        mv.addObject("newUserDto", new NewUserDto());
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Registro");
        mv.addObject("subtitulo","Crea tu cuenta :P");
        mv.addObject("titulo", "Registro");

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        mv.addObject("_csrf", csrfToken);
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("newUserDto") NewUserDto newUserDto,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {

        // Validaciones
        if (!newUserDto.isAcceptConditions()) {
            return getRegisterModelAndView(newUserDto, "Debes aceptar las condiciones de uso.", request);
        }

        if (newUserDto.getPassword() == null || newUserDto.getConfirmPassword() == null
                || !newUserDto.getPassword().equals(newUserDto.getConfirmPassword())) {
            return getRegisterModelAndView(newUserDto, "Las contraseñas no coinciden.", request);
        }

        if (appUserService.findByEmailIgnoreCase(newUserDto.getEmail()).isPresent()) {
            return getRegisterModelAndView(newUserDto, "Ya existe un usuario con ese correo electrónico.", request);
        }

        try {
            String encodedPassword = passwordEncoder.encode(newUserDto.getPassword());
            AppUser appUser = UserMapper.map(newUserDto, encodedPassword);
            appUserService.save(appUser);

            // Registro correcto → redirigir al login con mensaje flash
            redirectAttributes.addFlashAttribute("successMessage",
                    "Usuario registrado correctamente. ¡Ya puedes iniciar sesión!");
            return new ModelAndView("redirect:/users/login");

        } catch (Exception e) {
            return getRegisterModelAndView(newUserDto, "Se ha producido un error al registrar el usuario.", request);
        }
    }

    private ModelAndView getRegisterModelAndView(NewUserDto newUserDto, String errorMessage, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/register");
        mv.addObject("newUserDto", newUserDto);
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Registro");
        mv.addObject("subtitulo", "Crea tu cuenta :P");
        mv.addObject("titulo", "Registro");
        mv.addObject("error", errorMessage);

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        mv.addObject("_csrf", csrfToken);
        return mv;
    }
}
