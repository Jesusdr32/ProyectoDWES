package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewCategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findById(Long id);
    Product createNew(NewProductsDto newProductsDto);
    Product update(Long productId, NewProductsDto newProductsDto);
//    List<Product> findByCategoryId(Long categoryId); same
    //void deleteById(Long id);
}
