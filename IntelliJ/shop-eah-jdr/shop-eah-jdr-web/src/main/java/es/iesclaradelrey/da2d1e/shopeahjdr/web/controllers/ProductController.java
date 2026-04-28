package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.ProductDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    // Cuando alguien entra a /products/1
    @GetMapping("/{id}")
    public String redirectToSlug(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        return "redirect:/products/" + id + "/" + product.getSlug();
    }

    // Cuando alguien entra a /products/1/lo-que-sea
    @GetMapping("/{id}/{slug}")
    public ModelAndView productDetail(@PathVariable Long id,
                                      @PathVariable String slug) {

        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        String correctSlug = product.getSlug();

        // Si la url no coincide, redirige al correcto
        if (!slug.equals(correctSlug)) {
            return new ModelAndView("redirect:/products/" + id + "/" + correctSlug);
        }

        ModelAndView mv = new ModelAndView("product-detail");
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

//    @GetMapping("/search")
//    public ModelAndView search(
//            @RequestParam(value = "text", required = false)String  text,
//            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
//            @RequestParam(value = "brandId", required = false) Long brandId,
//            @RequestParam(value = "categoryId", required = false) Long categoryId,
//            @RequestParam(value = "size", required = false) Integer size,
//            @RequestParam(value = "sort", required = false) String sort,
//            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
//            ) {
//        ModelAndView mv = new ModelAndView("product/search");
//
//        mv.addObject("text", text);
//        mv.addObject("maxPrice", maxPrice);
//        mv.addObject("brandId", brandId);
//        mv.addObject("categoryId", categoryId);
//        mv.addObject("size", size);
//        mv.addObject("sort", sort);
//        mv.addObject("page", page);
//
//        boolean firtLoad =
//                (text == null || text.isBlank()) &&
//                        maxPrice == null &&
//                        brandId == null &&
//                        categoryId == null &&
//                        size == null &&
//                        (sort == null || sort.isBlank()) &&
//                        page == 0;
//        mv.addObject("searched", !firtLoad);
//
//        if(firtLoad){
//            return mv;
//        }
//        int pageSize = (size == null || size <= 0) ? 10 : size;
//
//        Sort springSort = buildSort(sort);
//        Pageable pageable = PageRequest.of(page, pageSize, springSort);
//
//        Page<Product> productPage = productService.searchProducts(
//                text,
//                maxPrice,
//                brandId,
//                categoryId,
//                pageable
//        );
//
//        mv.addObject("productPage", productPage);
//        mv.addObject("products", productPage.getContent());
//        mv.addObject("hasResults", !productPage.isEmpty());
//
//        int currentPage = productPage.getNumber();
//        int totalPages = productPage.getTotalPages();
//
//        int startPage = Math.max(0, currentPage - 2);
//        int endPage = Math.min(totalPages -1, currentPage + 2);
//
//        mv.addObject("startPage", startPage);
//        mv.addObject("endPage", endPage);
//        return mv;
//
//    }

    public record SelectOption(String value, String text){}

    @ModelAttribute("brands")
    public List<Brand> getBrands() {
        return brandService.findAll();
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @ModelAttribute("PageSizes")
    public List<Integer> getPageSizes() {
        return List.of(1, 2, 5, 10, 50);
    }

    @ModelAttribute("sortOptions")
    public List<SelectOption> getSortOptions() {
        return List.of(
                new SelectOption("nameAsc", "Nombre"),
                new SelectOption("priceAsc", "Precio (menor a mayor)"),
                new SelectOption("priceDesc", "Precio (mayor a menor"),
                new SelectOption("stockAsc", "Stock (menor a mayor"),
                new SelectOption("stockDesc", "Stock (mayor a menor)")
        );
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nameAsc") String sortBy,
            Model model) {

        Page<Product> foundProductPage = productService.search(text, maxPrice, brandId, categoryId, page, size, sortBy);

        // Añadir parámetros como atributos al modelo
        model.addAttribute("text", text);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("brandId", brandId);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);

        model.addAttribute("results", foundProductPage.toList());
        model.addAttribute("totalPages", foundProductPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "search";
    }

//    private Sort buildSort(String sort) {
//        if (sort == null || sort.isBlank() || sort.equals("NAME")) {
//            return Sort.by(Sort.Direction.ASC, "name");
//        }
//        return switch (sort) {
//            case "PRICE_ASC" -> Sort.by(Sort.Direction.ASC, "price");
//            case "PRICE_DESC" -> Sort.by(Sort.Direction.DESC, "price");
//            case "STOCK_ASC" -> Sort.by(Sort.Direction.ASC, "stock");
//            case "STOCK_DESC" -> Sort.by(Sort.Direction.DESC, "stock");
//            default -> Sort.by(Sort.Direction.ASC, "name");
//        };
//    }
}
