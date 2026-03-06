package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.NewUserDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;

import java.time.LocalDateTime;

public class UserMapper {

    private UserMapper() {}

    public static AppUser map(NewUserDto dto, String encodedPassword) {

        return AppUser.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .birthDate(dto.getBirthDate())
                .password(encodedPassword)
                .registrationDate(LocalDateTime.now())
                .build();
    }
}
