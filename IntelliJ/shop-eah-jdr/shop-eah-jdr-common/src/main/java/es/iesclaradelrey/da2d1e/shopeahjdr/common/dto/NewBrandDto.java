package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewBrandDto {
    private Long brandId;
    private String brandName;
    private String brandDescription;
    private String brandImage;
}
