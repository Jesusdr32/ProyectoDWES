package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final BrandService brandService;

    public HomeController(CategoryService categoryService, BrandService brandService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping({"/", ""})
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("title", "GEX - Inicio");
        mv.addObject("brands", brandService.findAll());
        return mv;
    }
    @GetMapping("/terms")
    public ModelAndView terms(){
        ModelAndView mv = new ModelAndView("terms");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Condiciones de Uso");
        mv.addObject("subtitulo", "Nuestras condicines");
        mv.addObject("titulo", "Condiciones de Uso");
        return mv;
    }
    @GetMapping("/about")
    public ModelAndView about() {
        ModelAndView mv = new ModelAndView("about");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Sobre Nosotros");
        mv.addObject("subtitulo", "Un poco mas :P");
        mv.addObject("titulo", "Sobre Nosotros");
        return mv;
    }
}
