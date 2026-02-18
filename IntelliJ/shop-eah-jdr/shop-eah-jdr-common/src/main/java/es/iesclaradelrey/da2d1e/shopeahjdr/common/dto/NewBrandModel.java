package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class NewBrandModel {
    private Long brandId;
    private String brandName;
    private String brandDescription;
    private String brandImage;
    private Set<Long> products = new HashSet<>();
}
