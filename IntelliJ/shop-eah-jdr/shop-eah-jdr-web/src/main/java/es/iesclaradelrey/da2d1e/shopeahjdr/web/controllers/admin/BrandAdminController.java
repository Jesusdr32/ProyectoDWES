package es.iesclaradelrey.da2d1e.shopeahjdr.web.controllers.admin;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewBrandModel;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.BrandServiceImpl;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BrandAdminController {

    private final CategoryServiceImpl categoryService;
    private final BrandServiceImpl brandService;

    public BrandAdminController(CategoryServiceImpl categoryService, BrandServiceImpl brandService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping( "/admin/brands")
    public ModelAndView adminBrand(){
        ModelAndView mv = new ModelAndView("admin/brands");
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
    @GetMapping("/admin/brands/")
    public String adminBrandRedirect() {
        return "redirect:/admin/brands";
    }

    @GetMapping("/admin/new-brand")
    public String getCreateBrand(Model model) {
        model.addAttribute("brand", new NewBrandModel());
        return "admin/new-brand";
    }

    @PostMapping("/admin/new-brand")
    public String newBrandPost(@ModelAttribute("brand")NewBrandModel newBrandModel, Model model) {
        System.out.println("Desarrolladora recibida:  " + newBrandModel);
     try {
         Brand newBrand = brandService.createNew(newBrandModel);
         return String.format("redirect:/admin/brands/%d", newBrand.getId());
     } catch (Exception e) {
         System.out.println("Se ha producido un error");
         e.printStackTrace();
         model.addAttribute("brand", brandService.findAll());
         return "admin/new-brand";
     }
    }
}
