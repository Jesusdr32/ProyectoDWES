package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewCategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.CategoryMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CategoryRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CategoryAdminController {

    private final CategoryServiceImpl categoryService;
    private final BrandServiceImpl brandService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public CategoryAdminController(CategoryServiceImpl categoryService, BrandServiceImpl brandService, ProductService productService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping( "/admin/categories/categories")
    public ModelAndView adminCategory(){
        ModelAndView mv = new ModelAndView("/admin/categories/categories");
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

    @GetMapping("/admin/categories/categories/")
    public String adminCategoryRedirect(){
        return "redirect:/admin/categories/categories";
    }


    // Crear una nueva categoría
    @GetMapping("/admin/categories/new")
    public String getCreateCategory(Model model) {
        model.addAttribute("category", new NewCategoryDto());
        model.addAttribute("products",  productService.findAll());
        model.addAttribute("title", "GEX - New Category");
        model.addAttribute("subtitulo", "Formulario de creación de categorías :P");
        model.addAttribute("titulo", "Nueva Categoría");
        return "admin/categories/new";
    }

    @PostMapping("/admin/categories/new")
    public String postCreateCategory(@ModelAttribute("category") NewCategoryDto newCategoryDto, Model model) {
        System.out.printf("Categoría recibida: \n%s\n", newCategoryDto);

        try {
            Category category = categoryService.createNew(newCategoryDto);
            return "redirect:/admin/categories/categories";
        } catch (Exception e) {
            model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
            model.addAttribute("products",productService.findAll());
            return "admin/categories/new";
        }
    }

    // Editar una categoria
    @GetMapping("/admin/categories/edit/{id}")
    public String getEditCategory(@PathVariable Long id,
                                  Model model) {
        Category category = categoryService
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No se encuentra la categoria con id %d", id)
                ));
        NewCategoryDto newCategoryDto = CategoryMapper.map(category);

        model.addAttribute("category", newCategoryDto);
        model.addAttribute("title", "GEX - Editar Categoria");
        model.addAttribute("titulo", "Editar Categoria");
        model.addAttribute("subtitulo", "Formulario de edición de categorias");
        return "admin/categories/edit";
    }


    @PostMapping("/admin/categories/edit/{id}")
    public String postEditCategory(@PathVariable Long id,
                                  @ModelAttribute("category") NewCategoryDto newCategoryDto,
                                  Model model) {
        try {
            categoryService.update(id, newCategoryDto);
            return "redirect:/admin/categories/categories";
        } catch (Exception e){
            model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
            model.addAttribute("category", newCategoryDto);
            model.addAttribute("title", "GEX - Editar Categoria");
            model.addAttribute("titulo", "Editar Categoria");
            model.addAttribute("subtitulo", "Formulario de edición de categorias");
            return "admin/categories/edit";
        }
    }

    // Eliminar una desarrolladora
    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategoryGet(@PathVariable Long id, Model model) {
        var category = categoryService.findById(id).orElseThrow();
        model.addAttribute("title", "GEX - Delete Category");
        model.addAttribute("subtitulo", "Formulario para la eliminación de categoría :P");
        model.addAttribute("titulo", "Eliminar Categoría");
        model.addAttribute("name", category.getName());
        model.addAttribute("url", "/admin/categories/categories");

        return "/admin/categories/delete";
    }

    @PostMapping("/admin/categories/delete/{id}")
    public String deleteCategoryPost(@PathVariable Long id, Model model) {
        try {
            categoryRepository.deleteById(id);
            return "admin/categories/categories";
        } catch(Exception e) {
            model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
            model.addAttribute("title", "GEX - Delete Category");
            var category = categoryService.findById(id).orElseThrow();
            model.addAttribute("subtitulo", "Formulario para la eliminación de categoría :P");
            model.addAttribute("titulo", "Eliminar Categoría");
            model.addAttribute("name", category.getName());
            model.addAttribute("url", "/admin/categories/categories");
            return "/admin/categories/delete";
        }
    }
}
