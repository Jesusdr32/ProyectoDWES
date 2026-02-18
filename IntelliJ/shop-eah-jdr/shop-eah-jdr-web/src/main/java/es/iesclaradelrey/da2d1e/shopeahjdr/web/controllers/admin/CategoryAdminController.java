package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewCategoryModel;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CategoryAdminController {

    private final CategoryServiceImpl categoryService;
    private final BrandServiceImpl brandService;
    private final ProductService productService;

    public CategoryAdminController(CategoryServiceImpl categoryService, BrandServiceImpl brandService, ProductService productService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productService = productService;
    }

    @GetMapping( "/admin/categories")
    public ModelAndView adminBrand(){
        ModelAndView mv = new ModelAndView("/admin/categories");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Admin Categorias");
        mv.addObject("subtitulo", "Administración de Categorias :P");
        mv.addObject("titulo", "Nuestra admin de Categorias");
        mv.addObject("fields", List.of("id", "name", "description"));
        mv.addObject("headers", List.of("ID", "Nombre", "Descripción"));
        mv.addObject("baseURL", "/admin/categories");
        return mv;
    }

    @GetMapping("/admin/categories/")
    public String adminCategoryRedirect(){
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/new-category")
    public String getCreateCategory(Model model) {
        model.addAttribute("category", new NewCategoryModel());
        return "/admin/new-category";
    }

    @PostMapping("/admin/new-category")
    public String postCreateCategory(@ModelAttribute("category") NewCategoryModel newCategoryModel, Model model) {
        System.out.printf("Categoría recibida: \n%s\n", newCategoryModel);

        try {
            Category newCategory = categoryService.createNew(newCategoryModel);

            return "redirect:/admin/categories";
        } catch (Exception e) {
            System.out.println("Se ha producido un error");
            e.printStackTrace();
            model.addAttribute("products",productService.findAll());
            return "/admin/new-category";
        }
    }
}
