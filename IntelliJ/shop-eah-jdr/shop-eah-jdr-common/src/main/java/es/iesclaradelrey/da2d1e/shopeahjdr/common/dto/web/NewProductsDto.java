package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.validation.UniqueProductName;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.validation.ValidEan13;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProductsDto {
    private Long productId;

    @ValidEan13
    @Size(max = 13, message = "El EAN no puede tener más de 13 dígitos")
    private String productEan;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    @UniqueProductName
    private String productName;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 4000, message = "La descripción no puede superar los 4000 caracteres")
    private String productDescription;

    @Size(max = 500, message = "La imagen no puede superar los 500 caracteres")
    private String productImage;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener como máximo 8 enteros y 2 decimales")
    private Double productPrice;

    @NotNull(message = "El descuento es obligatorio")
    @Min(value = 0, message = "El descuento no puede ser negativo")
    @Max(value = 100, message = "El descuento no puede ser mayor que 100")
    private Integer productDiscount;

    @NotNull(message = "La marca es obligatoria")
    private Long brandId;

    private Set<Long> categories = new HashSet<>();
}