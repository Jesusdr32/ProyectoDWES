package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/categories", "/categories/"})
    public ModelAndView categories() {
        ModelAndView mv = new ModelAndView("categories");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("defaultImage", "/images/categoriaGenerica.png");
        return mv;
    }
}
