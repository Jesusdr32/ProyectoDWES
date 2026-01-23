package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;

import java.util.Optional;

public interface GenericRepository<T, ID> {
    T save(T entity);

    Optional<T> findbyId(ID id);

    Iterable<T> findAll();

    void deleteById(ID id);

    boolean existsById(ID id);

    Optional<T> findById(ID id);
}
