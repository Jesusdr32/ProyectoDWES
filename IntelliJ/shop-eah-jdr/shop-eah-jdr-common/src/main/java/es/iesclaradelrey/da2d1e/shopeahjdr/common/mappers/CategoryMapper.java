package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewCategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;

public class CategoryMapper {

    private CategoryMapper() {}

    public static NewCategoryDto map(Category category) {
        return NewCategoryDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .categoryDescription(category.getDescription())
                .categoryImage(category.getImage())
                .build();
    }

    public static Category map(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .id(newCategoryDto.getCategoryId())
                .name(newCategoryDto.getCategoryName())
                .description(newCategoryDto.getCategoryDescription())
                .image(newCategoryDto.getCategoryImage())
                .build();
    }
}
