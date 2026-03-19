package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String password;
    private String confirmPassword;
    private boolean acceptConditions;
}
