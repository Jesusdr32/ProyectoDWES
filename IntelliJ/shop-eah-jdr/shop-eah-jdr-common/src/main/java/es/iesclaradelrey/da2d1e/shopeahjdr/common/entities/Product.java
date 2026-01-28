package es.iesclaradelrey.da2d1e.shopeahjdr.common.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Pattern(regexp = "\\d{13}", message = "El código EAN debe tener 13 dígitos")
    @Column(name = "ean", nullable = false, unique = true, length = 13)
    private String ean;

    @Column(name = "name",nullable = false, length = 200)
    private String name;

    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Column(name = "image", length = 500)
    private String image;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "discount", nullable = false)
    private Integer discount;
}
