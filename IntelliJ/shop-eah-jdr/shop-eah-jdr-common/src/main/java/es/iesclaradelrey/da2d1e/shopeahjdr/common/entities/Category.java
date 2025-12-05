package es.iesclaradelrey.da2d1e.shopeahjdr.common.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Category {
    private Long id;
    private String name;
    private String description;
    private String image;
}
