package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public ModelAndView admin() {
        ModelAndView mv = new ModelAndView("/admin/admin");
        mv.addObject("title", "GEX - Admin");
        mv.addObject("subtitulo", "Este es el AdminController :P");
        mv.addObject("titulo", "Administración de la aplicación");
        return mv;
    }

    @GetMapping("/admin/")
    public String adminRedirect() {
        return "redirect:/admin";
    }
}