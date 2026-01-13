package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController {

    @GetMapping({"/about", "/about/"})
    public String about(){
       return "forward:/about.html";
    }
}
