package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProductsDto {
    private Long productId;
    private String productEan;
    private String productName;
    private String productDescription;
    private String productImage;
    private Double productPrice;
    private Integer productDiscount;
    private Long brandId;
    private Set<Long> categories = new HashSet<>();
}
