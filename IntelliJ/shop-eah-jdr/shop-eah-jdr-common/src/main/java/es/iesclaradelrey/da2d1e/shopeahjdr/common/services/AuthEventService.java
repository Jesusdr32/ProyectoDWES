package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AuthEvent;

import java.util.Optional;

public interface AuthEventService {
    AuthEvent save(AuthEvent authEvent);

    Optional<AuthEvent> findById(long id);

    void  delete(AuthEvent authEvent);
}
