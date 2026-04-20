package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String productEan;
    private String productName;
    private String productDescription;
    private String productImage;
    private Double productPrice;
    private Integer productDiscount;
    private BrandDto brand;
    private Set<CategoryDto> categories;
}