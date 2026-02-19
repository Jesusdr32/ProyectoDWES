package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryImage;
}
