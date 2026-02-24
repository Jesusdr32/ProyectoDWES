package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.ProductMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.ProductRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductAdminController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductAdminController(CategoryService categoryService, BrandService brandService, ProductService productService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping( "/admin/products/products")
    public ModelAndView adminProduct(RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView("admin/products/products");
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
    @GetMapping("/admin/products/products/")
    public String adminProductRedirect() {
        return "redirect:/admin/products/products";
    }



    // Crear un nuevo producto
    @GetMapping("/admin/products/new")
    public String newProductGet(Model model){
        model.addAttribute("product", new NewProductsDto());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("title", "GEX - New Product");
        model.addAttribute("subtitulo", "Formulario de creación de productos :P");
        model.addAttribute("titulo", "Nuevo Producto");
        return "admin/products/new";
    }

    @PostMapping("/admin/products/new")
    public String newProductPost(@ModelAttribute("product") NewProductsDto newProductsDto, Model model){
        System.out.printf("Producto recibido: \n%s\n", newProductsDto);
        try {
            Product product = productService.createNew(newProductsDto);
            return "redirect:/admin/products/products";
        } catch (Exception e){
            model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
            model.addAttribute("category", categoryService.findAll());
            return "admin/products/new";
        }
    }

    // Editar un producto
    @GetMapping("/admin/products/edit/{id}")
    public String getEditProduct(@PathVariable Long id,
                                 Model model){
        Product product = productService
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No existe el producto con el id: %s", id)
                ));
        NewProductsDto newProductsDto = ProductMapper.map(product);

        model.addAttribute("product", newProductsDto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("title", "GEX - Editar Producto");
        model.addAttribute("titulo", "Editar Producto");
        model.addAttribute("subtitulo", "Formulario de edición de productos");
        return "admin/products/edit";
    }

    @PostMapping("/admin/products/edit/{id}")
    public String postEdiProduct(@PathVariable Long id,
                                 @ModelAttribute("product") NewProductsDto newProductsDto,
                                 Model model){
        try {
            productService.update(id, newProductsDto);
            return "redirect:/admin/products/products";
        } catch (Exception e) {
            model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
            model.addAttribute("product", newProductsDto);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("title", "GEX - Editar Producto");
            model.addAttribute("titulo", "Editar Producto");
            model.addAttribute("subtitulo", "Formulario de edición de productos");
            return "admin/products/edit";
        }
    }

    // Eliminar una desarrolladora
    @GetMapping("/admin/products/delete/{id}")
    public String deleteProductGet(@PathVariable Long id, Model model) {
        var product = productService.findById(id).orElseThrow();
        model.addAttribute("title", "GEX - Delete Product");
        model.addAttribute("subtitulo", "Formulario para la eliminación de producto :P");
        model.addAttribute("titulo", "Eliminar Producto");
        model.addAttribute("name", product.getName());
        model.addAttribute("url", "/admin/products/products");

        return "/admin/products/delete";
    }

    @PostMapping("/admin/products/delete/{id}")
    public String deleteProductPost(@PathVariable Long id, Model model) {
        try {
            productRepository.deleteById(id);
            return "redirect:/admin/products/products";
        } catch(Exception e) {
            model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
            var product = productService.findById(id).orElseThrow();
            model.addAttribute("brand", productService.findById(id));
            model.addAttribute("title", "GEX - Delete Product");
            model.addAttribute("subtitulo", "Formulario para la eliminación de producto :P");
            model.addAttribute("titulo", "Eliminar Producto");
            model.addAttribute("name", product.getName());
            model.addAttribute("url", "/admin/products/products");
            return "/admin/products/delete";
        }
    }
}
