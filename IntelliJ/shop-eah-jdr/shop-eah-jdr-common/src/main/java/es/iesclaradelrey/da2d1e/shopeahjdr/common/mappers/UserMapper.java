package es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api.UserDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewUserDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    NewUserDto map(AppUser user);
    AppUser map(NewUserDto user);

    //UserDto map(AppUser user);
    AppUser map(UserDto user);

    @Mapping(target = "password", source = "password")
    AppUser map(NewUserDto user, String password);

    @Mapping(target = "password", source = "password")
    AppUser map(UserDto user, String password);
}
