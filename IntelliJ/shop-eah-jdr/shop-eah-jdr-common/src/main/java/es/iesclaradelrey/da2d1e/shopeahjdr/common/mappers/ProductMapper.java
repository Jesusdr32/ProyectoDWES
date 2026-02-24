package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;

import java.util.stream.Collectors;

public class ProductMapper
{

    private ProductMapper() {}

    public static NewProductsDto map(Product product) {
        return NewProductsDto.builder()
                .productId(product.getId())
                .productEan(product.getEan())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .productImage(product.getImage())
                .productPrice(product.getPrice())
                .productDiscount(product.getDiscount())
                .brandId(product.getBrand().getId())
                .categories(product.getCategories().stream().map(Category::getId).collect(Collectors.toSet()))
                .build();
    }

    public static Product map(NewProductsDto newProductsDto) {
        return Product.builder()
                .id(newProductsDto.getProductId())
                .ean(newProductsDto.getProductEan())
                .name(newProductsDto.getProductName())
                .description(newProductsDto.getProductDescription())
                .image(newProductsDto.getProductImage())
                .price(newProductsDto.getProductPrice())
                .discount(newProductsDto.getProductDiscount())
                .build();
    }
}
