package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Category save(Category category);
    Optional<Category> findById(Long id);
}
