package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private String productName;
    private double unitPrice;
    private int discount;
    private double discountedPrice;
    private int units;
    private double totalPrice;
}
