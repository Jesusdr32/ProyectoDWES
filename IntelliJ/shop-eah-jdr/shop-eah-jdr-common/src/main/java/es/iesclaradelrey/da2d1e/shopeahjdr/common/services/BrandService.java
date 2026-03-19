package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> findAll();
    Brand save(Brand brand);
    Optional<Brand> findById(Long id);
    Brand createNew(NewBrandDto newBrandDto);
    Brand update(Long brandId, NewBrandDto newBrandDto);

}
