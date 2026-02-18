package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import jakarta.transaction.UserTransaction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class NewProductsModel {
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
