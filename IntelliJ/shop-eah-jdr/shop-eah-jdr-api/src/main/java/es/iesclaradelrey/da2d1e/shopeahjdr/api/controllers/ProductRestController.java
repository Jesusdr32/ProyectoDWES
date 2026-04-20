package es.iesclaradelrey.da2d1e.shopeahjdr.api.controllers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.ProductDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.ProductMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductRestController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(
                productService.findAll()
                        .stream()
                        .map(productMapper::mapApi)
                        .toList()
        );
    }

    // detalle de categoria para desarrollo de interfaces
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long productId) {
        return productService.findById(productId)
                .map(productMapper::mapApi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}