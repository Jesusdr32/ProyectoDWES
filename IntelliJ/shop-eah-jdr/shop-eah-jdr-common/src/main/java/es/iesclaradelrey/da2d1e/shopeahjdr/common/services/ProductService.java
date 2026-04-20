package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;

import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Product save(Product product);
    Optional<Product> findById(Long id);
    Product createNew(NewProductsDto newProductsDto);
    Product update(Long productId, NewProductsDto newProductsDto);

    List<Product> findByCategory(Long categoryId);

    String exportAllStax() throws XMLStreamException;

    void importProductsStax(InputStream productsStream) throws XMLStreamException;
//    List<Product> findByCategoryId(Long categoryId); same
    //void deleteById(Long id);
    boolean existsByProductName(String productName);
}
