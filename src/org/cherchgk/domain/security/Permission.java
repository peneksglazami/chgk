package org.cherchgk.domain.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Andrey Grigorov
 */
@Entity
public class Permission {
    @Id
    @GeneratedValue
    private Long id;

    private String permission;

    public Permission() {
    }

    public Permission(String permission) {
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
