package org.cherchgk.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Утилитарный класс, позволяющий получить доступ к использующейся реализации EntityManager.
 *
 * @author Andrey Grigorov
 */
public class EntityManagerProvider {

    private static EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        EntityManagerProvider.entityManager = entityManager;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}