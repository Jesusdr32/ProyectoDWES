package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductAdminController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;

    public ProductAdminController(CategoryService categoryService, BrandService brandService, ProductService productService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productService = productService;
    }

    @GetMapping( "/admin/products")
    public ModelAndView adminProduct(RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView("admin/products");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("products", productService.findAll());
        mv.addObject("title", "GEX - Admin Productos");
        mv.addObject("subtitulo", "Administración de Productos :P");
        mv.addObject("titulo", "Nuestra admin de Productos");
        mv.addObject("fields", List.of("id", "name", "price", "ean"));
        mv.addObject("headers", List.of("ID", "Nombre", "Precio", "EAN"));
        mv.addObject("baseURL", "/admin/products");
        return mv;
    }
    @GetMapping("/admin/products/")
    public String adminProductRedirect() {
        return "redirect:/admin/products";
    }
}
