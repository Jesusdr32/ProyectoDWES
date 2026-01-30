package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findById(Long id);
//    List<Product> findByCategoryId(Long categoryId); same
    //void deleteById(Long id);
}
