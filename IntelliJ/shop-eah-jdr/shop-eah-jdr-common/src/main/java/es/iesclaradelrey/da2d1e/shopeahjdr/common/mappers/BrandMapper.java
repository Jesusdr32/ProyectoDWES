package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;

public class BrandMapper {

    private BrandMapper() {}

    public static NewBrandDto map(Brand brand) {
        return NewBrandDto.builder()
                .brandId(brand.getId())
                .brandName(brand.getName())
                .brandDescription(brand.getDescription())
                .brandImage(brand.getImage())
                .build();
    }

    public static Brand map(NewBrandDto newBrandDto) {
        return Brand.builder()
                .id(newBrandDto.getBrandId())
                .name(newBrandDto.getBrandName())
                .description(newBrandDto.getBrandDescription())
                .image(newBrandDto.getBrandImage())
                .build();
    }
}
