package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;

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

    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping({"", "/"})
    public ModelAndView products() {
        ModelAndView mv = new ModelAndView("products");
        mv.addObject("products", productService.findAll());
        mv.addObject("defaultImage", "/images/products/imagenGenerica.png");
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView products(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("product-detail");
        mv.addObject("product", productService.findById(id).orElseThrow());
        return mv;
    }
}
