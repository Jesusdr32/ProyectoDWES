package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto;


import lombok.*;

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
    private LocalDate birthDate;
    private String password;
    private String confirmPassword;
    private boolean acceptConditions;
}
