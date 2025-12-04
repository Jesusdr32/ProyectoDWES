package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
}
