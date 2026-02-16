package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;
    private final CategoryService categoryService;

    public BrandController(BrandService brandService, CategoryService categoryService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public ModelAndView brands() {
        ModelAndView mv = new ModelAndView("brands");
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Desarrolladoras");
        mv.addObject("titulo", "Lista de Desarrolladoras");
        mv.addObject("subtitulo", "Descubre las desarrolladoras de nuestros productos :P");
        mv.addObject("categories", categoryService.findAll());
        return mv;
    }

    @GetMapping("/{id}/{*}")
    public ModelAndView brands(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("brand-detail");
        var brand = brandService.findById(id).orElseThrow();
        mv.addObject("brands", brandService.findAll());
        mv.addObject("brand", brandService.findById(id).orElseThrow());
        mv.addObject("title", "GEX - " + brand.getName());
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("titulo", "GEX - " + brand.getName());
        mv.addObject("subtitulo", "Desarrolladora más popular :P");
        return mv;
    }
}
