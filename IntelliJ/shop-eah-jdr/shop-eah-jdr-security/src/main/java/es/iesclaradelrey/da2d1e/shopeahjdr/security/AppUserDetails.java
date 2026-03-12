package es.iesclaradelrey.da2d1e.shopeahjdr.security;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class AppUserDetails implements UserDetails {

    private String username;
    @Getter
    private String email;
    private String password;

    @Getter
    private Long userId;

    private Collection<? extends GrantedAuthority> authorities;

    public AppUserDetails(AppUser appUser) {
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.password = appUser.getPassword();
        this.userId = appUser.getUserId();

        this.authorities = appUser.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getRol_id()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
