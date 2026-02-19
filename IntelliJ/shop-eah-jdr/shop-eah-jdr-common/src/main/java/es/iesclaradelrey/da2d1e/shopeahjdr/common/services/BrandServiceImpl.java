package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.BrandMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand createNew(NewBrandDto newBrandDto) {
        Brand brand = BrandMapper.map(newBrandDto);

        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Long brandId, NewBrandDto newBrandDto) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No se encuentra la desarrolladora con id %d", brandId)));
        brand.setName(newBrandDto.getBrandName());
        brand.setDescription(newBrandDto.getBrandDescription());
        brand.setImage(newBrandDto.getBrandImage());

        return  brandRepository.save(brand);
    }
}
