/*
 * Copyright 2012-2019 Andrey Grigorov, Anton Grigorov
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
package org.cherchgk.domain.security;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Верификационный токен, использующийся при самостоятельной
 * регистрации пользователей в системе, а также операции самостоятельного
 * восстановления пароля.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Token {

    public enum Type {
        SIGN_UP,
        RESTORE_PASSWORD
    }

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String uuid;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Column(nullable = false)
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
