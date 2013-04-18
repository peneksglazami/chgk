package org.cherchgk.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;

/**
 * Команда
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Entity
public class Team implements DomainObject {

    public enum Type {
        JUNIOR("Младшие школьники"),
        SENIOR("Старшие школьники");

        private String title;

        private Type(String title) {
            this.title = title;
        }

        public String getTitle() {        
            return title;
        }
        
        public String getName() {
            return name();
        }
    }

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer number;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tournament tournament;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}