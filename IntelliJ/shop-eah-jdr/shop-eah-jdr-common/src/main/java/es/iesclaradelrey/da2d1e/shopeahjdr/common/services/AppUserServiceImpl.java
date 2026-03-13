package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppRol;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.AppRolRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppRolRepository appRolRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository, AppRolRepository appRolRepository) {
        this.appUserRepository = appUserRepository;
        this.appRolRepository = appRolRepository;
    }

    @Override
    public AppUser save(AppUser appUser) {
        if (appUser.getRoles() == null || appUser.getRoles().isEmpty()) {
            AppRol rolUser = appRolRepository.findByRolIdIgnoreCase("USER").orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
            appUser.setRoles(Set.of(rolUser));
        }

        return appUserRepository.save(appUser);
    }

    @Override
    public Optional<AppUser> findByEmailIgnoreCase(String email) {
        return appUserRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<AppUser> findByUsernameIgnoreCase(String username) {
        return appUserRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }


}
