package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewCategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Optional<Category> findById(Long id);
    Category createNew(NewCategoryDto newCategoryDto);
    Category update(Long categoryId, NewCategoryDto newCategoryDto);
}
