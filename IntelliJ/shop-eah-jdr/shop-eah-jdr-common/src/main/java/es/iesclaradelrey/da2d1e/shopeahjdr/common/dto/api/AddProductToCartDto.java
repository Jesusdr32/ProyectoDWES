package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductToCartDto {
    private Long productId;
    private int units;
}
