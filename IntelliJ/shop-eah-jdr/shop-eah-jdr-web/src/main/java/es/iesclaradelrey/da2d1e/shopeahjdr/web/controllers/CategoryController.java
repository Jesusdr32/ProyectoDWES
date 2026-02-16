package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BrandService brandService;

    public CategoryController(CategoryService categoryService, BrandService brandService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping({"", "/"})
    public ModelAndView categories() {
        ModelAndView mv = new ModelAndView("categories");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Categorias");
        mv.addObject("titulo", "Categoría de Videojuegos");
        mv.addObject("subtitulo", "Descubre diferentes categorias :P");
        return mv;
    }
    @GetMapping("/{id}")
    public ModelAndView categories(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("category-detail");
        var category = categoryService.findById(id).orElseThrow();
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("category", categoryService.findById(id).orElseThrow());
        mv.addObject("title", "GEX - " + category.getName());
        mv.addObject("titulo", "GEX - " + category.getName());
        mv.addObject("subtitulo", "Categoria mas jugada :P");
        return mv;
    }
}
