package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewCategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> findAll();
    Brand save(Brand brand);
    Optional<Brand> findById(Long id);
    Brand createNew(NewBrandDto newBrandDto);
    Brand update(Long brandId, NewBrandDto newBrandDto);

}
