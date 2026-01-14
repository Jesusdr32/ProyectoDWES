package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;

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

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/"})
    public ModelAndView categories() {
        ModelAndView mv = new ModelAndView("categories");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("defaultImage", "/images/categoriaGenerica.png");
        return mv;
    }
    @GetMapping("/{id}")
    public ModelAndView categories(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView("category-detail");
        mv.addObject("category", categoryService.findById(id).orElseThrow());
        return mv;
    }
}
