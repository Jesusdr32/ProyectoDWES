package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewUserDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.UserMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.AppUserService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    private final UserMapper userMapper;

    public RegisterController(AppUserService appUserService,
                              PasswordEncoder passwordEncoder,
                              CategoryService categoryService,
                              BrandService brandService,
                              UserMapper userMapper) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.userMapper = userMapper;
    }

    @GetMapping("/register")
    public ModelAndView registerForm(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/register");
        mv.addObject("newUserDto", new NewUserDto());
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Registro");
        mv.addObject("subtitulo", "Crea tu cuenta :P");
        mv.addObject("titulo", "Registro");

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        mv.addObject("_csrf", csrfToken);
        return mv;
    }
    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("newUserDto") NewUserDto newUserDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {

        if (newUserDto.getPassword() != null && newUserDto.getConfirmPassword() != null
                && !newUserDto.getPassword().equals(newUserDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Las contraseñas no coinciden.");
        }

        if (appUserService.findByEmailIgnoreCase(newUserDto.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "email.duplicate", "Ya existe un usuario con ese correo electrónico.");
        }

        if (bindingResult.hasErrors()) {
            return getRegisterModelAndView(newUserDto, request);
        }
        try {
            String encodedPassword = passwordEncoder.encode(newUserDto.getPassword());
            AppUser appUser = userMapper.map(newUserDto, encodedPassword);
            appUserService.save(appUser);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Usuario registrado correctamente. ¡Ya puedes iniciar sesión!"
            );
            return new ModelAndView("redirect:/users/login");

        } catch (Exception e) {
            bindingResult.reject("global.error", "Se ha producido un error al registrar el usuario.");
            return getRegisterModelAndView(newUserDto, request);
        }
    }

    private ModelAndView getRegisterModelAndView(NewUserDto newUserDto, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/register");
        mv.addObject("newUserDto", newUserDto);
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Registro");
        mv.addObject("subtitulo", "Crea tu cuenta :P");
        mv.addObject("titulo", "Registro");

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        mv.addObject("_csrf", csrfToken);
        return mv;
    }
}