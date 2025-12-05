package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    SortedMap<Long, Category> categories = new TreeMap<>();

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }


}
