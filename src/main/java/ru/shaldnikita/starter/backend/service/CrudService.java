package ru.shaldnikita.starter.backend.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.shaldnikita.starter.app.HasLogger;
import ru.shaldnikita.starter.backend.model.AbstractEntity;


public abstract class CrudService<T extends AbstractEntity> implements HasLogger {

    protected abstract JpaRepository<T, Long> getRepository();

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public void delete(long id) {
        getRepository().delete(id);
    }

    public T load(long id) {
        return getRepository().findOne(id);
    }

    public long countAnyMatching(T probe) {
        if (probe == null)
            return getRepository().count();
        else
            return getRepository().count(Example.of(probe));
    }

    public Page<T> findAnyMatching(T probe, Pageable pageable) {

        if (probe == null)
            return getRepository().findAll(pageable);
        else
            return getRepository().findAll(Example.of(probe), pageable);
    }
}