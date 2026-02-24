package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewCategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.BrandMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.CategoryMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.BrandRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BrandAdminController {

    private final CategoryServiceImpl categoryService;
    private final BrandServiceImpl brandService;
    private final ProductService productService;
    private final BrandRepository brandRepository;

    public BrandAdminController(CategoryServiceImpl categoryService, BrandServiceImpl brandService, ProductService productService, BrandRepository brandRepository) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productService = productService;
        this.brandRepository = brandRepository;
    }

    @GetMapping( "/admin/brands/brands")
    public ModelAndView adminBrand(){
        ModelAndView mv = new ModelAndView("admin/brands/brands");
        mv.addObject("categories", categoryService.findAll());
        mv.addObject("brands", brandService.findAll());
        mv.addObject("title", "GEX - Admin Desarrolladoras");
        mv.addObject("subtitulo", "Administración de Desarrolladoras :P");
        mv.addObject("titulo", "Nuestra admin de desarrolladoras");
        mv.addObject("fields", List.of("id", "name", "description"));
        mv.addObject("headers", List.of("ID", "Nombre", "Descripción"));
        mv.addObject("baseURL", "/admin/brands");
        return mv;
    }
    @GetMapping("/admin/brands/brands/")
    public String adminBrandRedirect() {
        return "redirect:/admin/brands/brands";
    }


    // Crear una nueva desarrolladora
    @GetMapping("/admin/brands/new")
    public String getCreateBrand(Model model) {
        model.addAttribute("brand", new NewBrandDto());
        model.addAttribute("title", "GEX - New Brand");
        model.addAttribute("subtitulo", "Formulario de creación de desarrolladora :P");
        model.addAttribute("titulo", "Nueva Desarrolladora");
        return "admin/brands/new";
    }

    @PostMapping("/admin/brands/new")
    public String newBrandPost(@ModelAttribute("brand") NewBrandDto newBrandDto, Model model) {
        System.out.println("Desarrolladora recibida:  " + newBrandDto);
     try {
         Brand brand = brandService.createNew(newBrandDto);
         return "redirect:/admin/brands/brands";
     } catch (Exception e) {
         model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
         model.addAttribute("brand", new NewBrandDto());
         model.addAttribute("title", "GEX - New Brand");
         model.addAttribute("subtitulo", "Formulario de creación de desarrolladora :P");
         model.addAttribute("titulo", "Nueva Desarrolladora");
         return "admin/brands/new";
     }
    }

    // Editar una desarrolladora
    @GetMapping("/admin/brands/edit/{id}")
    public String getEditBrands(@PathVariable Long id,
                                  Model model) {
        Brand brand = brandService
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No se encuentra la desarrolladora con id %d", id)
                ));
        NewBrandDto newBrandDto = BrandMapper.map(brand);

        model.addAttribute("brand",  newBrandDto);
        model.addAttribute("title", "GEX - Editar Categoria");
        model.addAttribute("titulo", "Editar Categoria");
        model.addAttribute("subtitulo", "Formulario de edición de categorias");
        return "admin/brands/edit";
    }

    @PostMapping("/admin/brands/edit/{id}")
    public String postEditBrands(@PathVariable Long id,
                                   @ModelAttribute("brands") NewBrandDto newBrandDto,
                                   Model model) {
        try {
            brandService.update(id, newBrandDto);
            return "redirect:/admin/brands/brands";
        } catch (Exception e){
            model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
            model.addAttribute("brand",  newBrandDto);
            model.addAttribute("title", "GEX - Editar Categoria");
            model.addAttribute("titulo", "Editar Categoria");
            model.addAttribute("subtitulo", "Formulario de edición de categorias");
            return "admin/brands/edit";
        }
    }

    // Eliminar una desarrolladora
    @GetMapping("/admin/brands/delete/{id}")
    public String deleteBrandGet(@PathVariable Long id, Model model) {
        var brand = brandService.findById(id).orElseThrow();
        model.addAttribute("title", "GEX - Delete Brand");
        model.addAttribute("subtitulo", "Formulario para la eliminación de desarrolladora :P");
        model.addAttribute("titulo", "Eliminar Desarrolladora");
        model.addAttribute("name", brand.getName());
        model.addAttribute("url", "/admin/brands/brands");

        return "admin/brands/delete";
    }

    @PostMapping("/admin/brands/delete/{id}")
    public String deleteBrandPost(@PathVariable Long id, Model model) {
        try {
            brandRepository.deleteById(id);
            return "admin/brands/brands";
        } catch(Exception e) {
            model.addAttribute("error", String.format("Se ha producido un error: %s", e.getMessage()));
            var brand = brandService.findById(id).orElseThrow();
            model.addAttribute("title", "GEX - Delete Brand");
            model.addAttribute("subtitulo", "Formulario para la eliminación de desarrolladora :P");
            model.addAttribute("titulo", "Eliminar Desarrolladora");
            model.addAttribute("name", brand.getName());
            model.addAttribute("url", "/admin/brands/brands");
            return "/admin/brands/delete";
        }
    }
}
