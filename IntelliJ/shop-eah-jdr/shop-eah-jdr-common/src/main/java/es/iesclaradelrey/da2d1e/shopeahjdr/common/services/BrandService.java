package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> findAll();
    Brand save(Brand brand);
    Optional<Brand> findById(Long id);
}
