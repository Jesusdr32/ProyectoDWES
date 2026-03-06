package es.iesclaradelrey.da2d1e.shopeahjdr.security;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AppUserDetails implements UserDetails {

    private String username;
    private String password;

    public AppUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AppUserDetails(AppUser appUser) {
        this.username = appUser.getEmail();
        this.password = appUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // de momento sin roles asignados
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
