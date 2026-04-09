package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.ProductDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, BrandMapper.class})
public interface ProductMapper {

    NewProductsDto map(Product product);

    @Mapping(target = "categories", ignore = true)
    Product map(NewProductsDto product);

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "productEan", source = "ean")
    @Mapping(target = "productName", source = "name")
    @Mapping(target = "productDescription", source = "description")
    @Mapping(target = "productImage", source = "image")
    @Mapping(target = "productPrice", source = "price")
    @Mapping(target = "productDiscount", source = "discount")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", source = "categories")
    ProductDto mapApi(Product product);
}