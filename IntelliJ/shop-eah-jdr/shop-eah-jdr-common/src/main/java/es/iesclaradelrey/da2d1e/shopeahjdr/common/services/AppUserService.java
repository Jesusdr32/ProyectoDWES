package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppUser;

import java.util.Optional;

public interface AppUserService {
    AppUser save(AppUser appUser);

    Optional<AppUser> findByEmailIgnoreCase(String email);

    Optional<AppUser> findByUsernameIgnoreCase(String username);

    Optional<AppUser> findById(Long id);

    boolean existsByUsername(String username);
}
