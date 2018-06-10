/*
 * Copyright 2012-2018 Andrey Grigorov, Anton Grigorov
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