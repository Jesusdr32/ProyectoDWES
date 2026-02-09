package es.iesclaradelrey.da2d1e.shopeahjdr.common.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brand")
@Getter
@Setter
@ToString
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String image;

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>();
}
