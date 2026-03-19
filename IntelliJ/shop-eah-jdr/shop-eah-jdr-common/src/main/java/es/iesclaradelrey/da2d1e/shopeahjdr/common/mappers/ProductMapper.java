package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    NewProductsDto map(Product product);
    @Mapping(target = "categories", ignore = true)
    Product map(NewProductsDto product);
}
