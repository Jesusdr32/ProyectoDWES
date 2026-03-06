package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewUserDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.UserMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.AppUserService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
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
    public ModelAndView registerForm() {
        ModelAndView mv = new ModelAndView("register");
        mv.addObject("newUserDto", new NewUserDto());
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Registro");
        mv.addObject("subtitulo","Crea tu cuenta");
        mv.addObject("titulo", "Registro");
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("newUserDto") NewUserDto newUserDto) {
        ModelAndView mv = new ModelAndView("register");
        mv.addObject("newUserDto", new NewUserDto());
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Registro");
        mv.addObject("subtitulo","Crea tu cuenta");
        mv.addObject("titulo", "Registro");
        mv.addObject("newUserDto", newUserDto);

        if (!newUserDto.isAcceptConditions()) {
            mv.addObject("error", "Debes aceptar las condiciones de uso.");
            return mv;
        }

        if (newUserDto.getPassword() == null || newUserDto.getConfirmPassword() == null
                || !newUserDto.getPassword().equals(newUserDto.getConfirmPassword())) {
            mv.addObject("error", "Las contraseñas no coinciden.");
            return mv;
        }
        if (appUserService.findByEmailIgnoreCase(newUserDto.getEmail()).isPresent()) {
            mv.addObject("error", "Ya existe un usuario con ese correo electrónico.");
            return mv;
        }
        try {
            String encodedPassword = passwordEncoder.encode(newUserDto.getPassword());
            AppUser appUser = UserMapper.map(newUserDto, encodedPassword);

            appUserService.save(appUser);

            return new ModelAndView("redirect:/register");
        } catch (Exception e) {
            mv.addObject("error", "Se ha producido un error al registrar el usuario.");
            return mv;
        }
    }
}
