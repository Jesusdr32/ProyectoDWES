package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.CategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewCategoryDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    NewCategoryDto map(Category category);
    Category map(NewCategoryDto category);

    Category mapApi(CategoryDto category);
    CategoryDto mapApi(Category category);

//    Set<Long> map(Set<Category> categories);
//    Set<Category> map(Set<Long> categoriesIds);

    default Long mapToId(Category category) {
        return category.getId();
    }
//
//    default Category map(Long categoryId, CategoryRepository categoryRepository) {
//        return  categoryRepository.getReferenceById(categoryId);
//    }

}
