package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewProductsModel;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.BrandRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CategoryRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createNew(NewProductsModel newProductsModel) {

        Set<Category> categories = Set.copyOf(categoryRepository.findAllById(newProductsModel.getCategories()));

        Optional<Brand> brand = brandRepository.findById(newProductsModel.getBrandId());

        if (categories.size()!=newProductsModel.getCategories().size()){
            throw new EntityNotFoundException("Alguno de los módulos no se han encontrado");
        }

        Product product = Product.builder()
                .id(newProductsModel.getProductId())
                .ean(newProductsModel.getProductEan())
                .name(newProductsModel.getProductName())
                .description(newProductsModel.getProductDescription())
                .image(newProductsModel.getProductImage())
                .price(newProductsModel.getProductPrice())
                .discount(newProductsModel.getProductDiscount())
                .brand(brand)
                .categories(categories)
                .build();

        return productRepository.save(product);
    }

//    @Override
//    public List<Product> findByCategoryId(Long categoryId) {
//        return productRepository.findByCategoryId(categoryId); same
//    }

    //@Override
    //public void deleteById(Long id) {
        //productRepository.deleteById(id);
    //}
}
