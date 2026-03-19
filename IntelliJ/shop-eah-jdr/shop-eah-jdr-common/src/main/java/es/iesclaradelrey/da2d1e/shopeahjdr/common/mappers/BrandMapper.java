package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.BrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    NewBrandDto map(Brand brand);
    Brand map(NewBrandDto brand);

//    BrandDto map(Brand brand);
    Brand map(BrandDto brand);
}
