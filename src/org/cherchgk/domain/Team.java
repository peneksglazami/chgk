package org.cherchgk.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Команда
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Entity
public class Team implements DomainObject {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer number;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private TeamCategory teamCategory;
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

    public TeamCategory getTeamCategory() {
        return teamCategory;
    }

    public void setTeamCategory(TeamCategory teamCategory) {
        this.teamCategory = teamCategory;
    }
}