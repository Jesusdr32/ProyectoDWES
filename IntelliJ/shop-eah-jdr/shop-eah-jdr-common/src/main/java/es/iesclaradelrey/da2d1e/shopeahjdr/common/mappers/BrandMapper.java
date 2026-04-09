package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.BrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    NewBrandDto mapWeb(Brand brand);
    Brand mapWeb(NewBrandDto brand);

    @Mapping(target = "brandId", source = "id")
    @Mapping(target = "brandName", source = "name")
    @Mapping(target = "brandDescription", source = "description")
    @Mapping(target = "brandImage", source = "image")
    BrandDto mapApi(Brand brand);

    @Mapping(target = "id", source = "brandId")
    @Mapping(target = "name", source = "brandName")
    @Mapping(target = "description", source = "brandDescription")
    @Mapping(target = "image", source = "brandImage")
    @Mapping(target = "products", ignore = true)
    Brand mapApi(BrandDto brand);
}