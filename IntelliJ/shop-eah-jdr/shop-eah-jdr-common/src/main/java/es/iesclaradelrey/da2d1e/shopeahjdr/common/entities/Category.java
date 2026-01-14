package es.iesclaradelrey.da2d1e.shopeahjdr.common.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
    private Long id;
    private String name;
    private String description;
    private String image;
}