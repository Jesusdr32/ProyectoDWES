package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class NewCategoryModel {
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
    private String categoryImage;
    private Set<Long> products = new HashSet<>();
}
