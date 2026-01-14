package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    final SortedMap<Long, Category> categories = new TreeMap<>();

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Category save(Category category) {
        //Si el estudiante llega con ID null es que es nuevo
        if (category.getId() == null) {
            synchronized (categories) {
                category.setId(getNewId());
                categories.put(category.getId(), category);
            }
            return category;
        }
        //si no tenía ID null, pero no se encuentra, error
        if (categories.get(category.getId()) == null) {
            throw new NoSuchElementException("Category with id " + category.getId() + " not found");
        }
        //Actualizar el existente
        return categories.put(category.getId(), category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(categories.get(id));
    }

    private Long getNewId() {
        return categories.isEmpty() ? 1 : categories.lastKey() + 1;
    }


}
