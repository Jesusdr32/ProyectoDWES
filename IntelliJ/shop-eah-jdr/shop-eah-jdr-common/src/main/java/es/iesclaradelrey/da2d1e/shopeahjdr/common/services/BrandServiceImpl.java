package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewBrandModel;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.BrandRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public BrandServiceImpl(BrandRepository brandRepository, ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
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
    public Brand createNew(NewBrandModel newBrandModel) {
        Set<Product> products = Set.copyOf(productRepository.findAllById(newBrandModel.getProducts()));

        if (products.size() != newBrandModel.getProducts().size()) {
            throw new EntityNotFoundException("Alguno de los productos no se han encontrado");
        }

        Brand brand = Brand.builder()
                .id(newBrandModel.getBrandId())
                .name(newBrandModel.getBrandName())
                .description(newBrandModel.getBrandDescription())
                .image(newBrandModel.getBrandImage())
                .products(products)
                .build();

        return brandRepository.save(brand);
    }
}
