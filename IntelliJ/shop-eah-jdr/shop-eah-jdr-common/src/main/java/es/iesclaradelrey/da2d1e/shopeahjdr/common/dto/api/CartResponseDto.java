package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {
    private List<CartItemDto> products;
    private long distinctProducts;
    private long totalUnits;
    private double totalPrice;
}
