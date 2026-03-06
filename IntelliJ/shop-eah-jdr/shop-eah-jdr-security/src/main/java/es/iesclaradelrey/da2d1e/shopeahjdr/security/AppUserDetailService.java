package es.iesclaradelrey.da2d1e.shopeahjdr.security;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.AppUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {

    private AppUserService appUserService;

    public AppUserDetailService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserService.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado el usuario con email: " + username));

        return new AppUserDetails(appUser);
    }
}
