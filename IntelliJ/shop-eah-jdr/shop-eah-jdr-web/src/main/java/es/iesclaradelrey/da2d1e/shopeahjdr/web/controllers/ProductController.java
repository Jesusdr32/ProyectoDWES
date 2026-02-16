package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService) { this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping({"", "/"})
    public ModelAndView products() {
        ModelAndView mv = new ModelAndView("products");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("products", productService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Productos");
        mv.addObject("titulo", "Videojuegos");
        mv.addObject("subtitulo", "Descubre diferentes Juegos :P");
        return mv;
    }

//    @GetMapping("/{id}")
//    public ModelAndView products(@PathVariable("id") Long id) {
//        ModelAndView mv = new ModelAndView("product-detail");
//        mv.addObject("product", productService.findById(id).orElseThrow());
//        return mv;
//    }
    @GetMapping("/{id}/{*}")
    public ModelAndView productDetail(@PathVariable("id") Long id) {
            ModelAndView mv = new ModelAndView("product-detail");
            Product product = productService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
            mv.addObject("product", product);
            mv.addObject("categories", categoryService.findAll());
            mv.addObject("brands", brandService.findAll());
            mv.addObject("title", "GEX - " + product.getName());
            mv.addObject("titulo", "GEX - " + product.getName());
            mv.addObject("subtitulo", "El juego más jugado :P");
            return mv;
        }
//    @GetMapping("/category/{categoryId}")
//    public ModelAndView productsByCategory(@PathVariable("categoryId") Long categoryId) {
//        ModelAndView mv = new ModelAndView("products");
//        mv.addObject("products", productService.findByCategoryId(categoryId));
//        return mv;
//    }
}
