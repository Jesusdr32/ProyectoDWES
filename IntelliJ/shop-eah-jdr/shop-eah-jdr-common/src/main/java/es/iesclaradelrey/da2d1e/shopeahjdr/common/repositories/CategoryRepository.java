package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    Category save(Category category);
}
