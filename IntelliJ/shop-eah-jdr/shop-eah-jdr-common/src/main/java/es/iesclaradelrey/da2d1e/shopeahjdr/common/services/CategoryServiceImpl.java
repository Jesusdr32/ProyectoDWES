package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewCategoryModel;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CategoryRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createNew(NewCategoryModel newCategoryModel) {
        Set<Product> products = Set.copyOf(productRepository.findAllById(newCategoryModel.getProducts()));

        if (products.size() != newCategoryModel.getProducts().size()) {
            throw new EntityNotFoundException("Alguno de los módulos no se han encontrado");
        }

        Category category = Category.builder()
                .id(newCategoryModel.getCategoryId())
                .name(newCategoryModel.getCategoryName())
                .description(newCategoryModel.getCategoryDescription())
                .image(newCategoryModel.getCategoryImage())
                .products(products)
                .build();

        return categoryRepository.save(category);
    }
}
