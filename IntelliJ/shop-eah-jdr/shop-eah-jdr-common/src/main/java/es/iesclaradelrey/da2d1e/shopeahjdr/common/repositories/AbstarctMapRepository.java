package es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstarctMapRepository<T, ID> implements GenericRepository<T, ID> {
    protected Map<ID, T> data = new HashMap<>();

    @Override
    public T save(T entity) {
        data.put(getId(entity), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return data.values();
    }

    @Override
    public void deleteById(ID id) {
        data.remove(id);
    }

    protected abstract ID getId(T entity);
}
