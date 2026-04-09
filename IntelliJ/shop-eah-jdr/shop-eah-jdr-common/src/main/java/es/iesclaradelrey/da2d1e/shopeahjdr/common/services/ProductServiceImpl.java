package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.ProductMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.BrandRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CategoryRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll(Sort.by("name").ascending());
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
    public Product createNew(NewProductsDto newProductsDto) {

        Set<Category> categories = Set.copyOf(categoryRepository.findAllById(newProductsDto.getCategories()));

        Brand brand = brandRepository.findById(newProductsDto.getBrandId()).orElseThrow(() -> new EntityNotFoundException(String.format("La desarrolladora con id %s no existe", newProductsDto.getBrandId())));

        if (categories.size()!= newProductsDto.getCategories().size()){
            throw new EntityNotFoundException("Alguno de los módulos no se han encontrado");
        }

        if (newProductsDto.getProductImage().isEmpty()) {
            newProductsDto.setProductImage(null);
        }

        Product product = productMapper.map(newProductsDto);
        product.setBrand(brand);
        product.setCategories(categories);

        return productRepository.save(product);
    }

    @Override
    public Product update(Long productId, NewProductsDto newProductsDto) {
        if (newProductsDto.getProductImage().isEmpty()) {
            newProductsDto.setProductImage(null);
        }

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("El producto con id %s no existe", productId)));

        // Actualizamos
        product.setEan(newProductsDto.getProductEan());
        product.setName(newProductsDto.getProductName());
        product.setDescription(newProductsDto.getProductDescription());
        product.setImage(newProductsDto.getProductImage());
        product.setPrice(newProductsDto.getProductPrice());
        product.setDiscount(newProductsDto.getProductDiscount());

        // Actulizamos el brand ya que estamos en el lado propietario
        Brand brand = brandRepository.getReferenceById(newProductsDto.getBrandId());
        product.setBrand(brand);

        // Tambien es desde el lado propietario
        Set<Category> newCategories = new HashSet<>(categoryRepository.findAllById(newProductsDto.getCategories()));
        product.setCategories(newCategories);

        return productRepository.save(product);
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoriesId(categoryId, Sort.by("name").ascending());
    }

    //@Override
    //public void deleteById(Long id) {
        //productRepository.deleteById(id);
    //}
}
