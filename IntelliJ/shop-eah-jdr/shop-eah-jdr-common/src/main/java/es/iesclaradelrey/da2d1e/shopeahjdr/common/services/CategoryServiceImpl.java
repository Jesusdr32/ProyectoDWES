package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewCategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.CategoryMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CategoryRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryMapper = categoryMapper;
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
    public Category createNew(NewCategoryDto newCategoryDto) {

        Category category = categoryMapper.map(newCategoryDto);

        if (newCategoryDto.getCategoryImage().isEmpty()) {
            newCategoryDto.setCategoryImage(null);
        }

        return categoryRepository.save(category);
    }


    @Override
    public Category update(Long categoryId, NewCategoryDto newCategoryDto) {
        if (newCategoryDto.getCategoryImage().isEmpty()) {
            newCategoryDto.setCategoryImage(null);
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No se encuentra la categoria con id %d", categoryId)));
        category.setName(newCategoryDto.getCategoryName());
        category.setDescription(newCategoryDto.getCategoryDescription());
        category.setImage(newCategoryDto.getCategoryImage());

        return categoryRepository.save(category);
    }


}
