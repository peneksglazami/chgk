/*
 * Copyright 2012-2016 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cherchgk.services;

import org.cherchgk.domain.DomainObject;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Transactional(rollbackFor = Throwable.class)
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
