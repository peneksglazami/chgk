package org.cherchgk.services;

import org.cherchgk.domain.DomainObject;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Transactional
public abstract class AbstractService<T extends DomainObject> implements DataService<T> {

    protected EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(T entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
