package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.AuthEvent;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.AuthEventRepository;

import java.util.Optional;

public class AuthEventServiceImpl implements AuthEventService {

    private final AuthEventRepository authEventRepository;

    public AuthEventServiceImpl(AuthEventRepository authEventRepository) {
        this.authEventRepository = authEventRepository;
    }

    @Override
    public AuthEvent save(AuthEvent authEvent) {
        return authEventRepository.save(authEvent);
    }

    @Override
    public Optional<AuthEvent> findById(long id) {
        return authEventRepository.findById(id);
    }

    @Override
    public void  delete(AuthEvent authEvent) {
        authEventRepository.delete(authEvent);
    }
}
