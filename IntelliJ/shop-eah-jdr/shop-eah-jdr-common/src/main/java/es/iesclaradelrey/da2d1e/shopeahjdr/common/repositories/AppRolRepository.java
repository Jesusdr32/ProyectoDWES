package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AppRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRolRepository extends JpaRepository<AppRol, Long> {
    Optional<AppRol> findByRolIdIgnoreCase(String name);
}
