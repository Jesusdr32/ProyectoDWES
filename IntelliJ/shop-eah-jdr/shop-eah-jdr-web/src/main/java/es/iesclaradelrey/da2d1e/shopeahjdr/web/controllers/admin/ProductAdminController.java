package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductAdminController {
    private final CategoryServiceImpl categoryService;
    private final BrandServiceImpl brandService;

    public ProductAdminController(CategoryServiceImpl categoryService, BrandServiceImpl brandService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping( "/admin/products")
    public ModelAndView adminBrand(RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView("admin/products");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Admin Productos");
        mv.addObject("subtitulo", "Administración de Productos :P");
        mv.addObject("titulo", "Nuestra admin de Productos");
        return mv;
    }
    @GetMapping("/admin/products/")
    public String adminProductRedirect() {
        return "redirect:/admin/products";
    }
}
