package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryAdminController {

    private final CategoryServiceImpl categoryService;
    private final BrandServiceImpl brandService;

    public CategoryAdminController(CategoryServiceImpl categoryService, BrandServiceImpl brandService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping( "/admin/categories")
    public ModelAndView adminBrand(){
        ModelAndView mv = new ModelAndView("admin/categories");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Admin Categorias");
        mv.addObject("subtitulo", "Administración de Categorias :P");
        mv.addObject("titulo", "Nuestra admin de Categorias");
        return mv;
    }

    @GetMapping("/admin/categories/")
    public String adminCategoryRedirect(){
        return "redirect:/admin/categories";
    }
}
