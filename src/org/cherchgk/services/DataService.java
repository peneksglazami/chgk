package org.cherchgk.services;

import org.cherchgk.domain.DomainObject;

import java.util.List;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public interface DataService<T extends DomainObject> {

    void save(T entity);

    T find(Long id);

    List<T> findAll();

    void delete(T entity);
}
