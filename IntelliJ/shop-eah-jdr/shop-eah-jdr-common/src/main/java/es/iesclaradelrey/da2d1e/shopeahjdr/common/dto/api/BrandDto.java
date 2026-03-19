package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
    private Long brandId;
    private String brandName;
    private String brandDescription;
    private String brandImage;
}
