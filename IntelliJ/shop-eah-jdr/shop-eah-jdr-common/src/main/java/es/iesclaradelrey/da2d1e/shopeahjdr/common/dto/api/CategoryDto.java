package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryImage;
}
