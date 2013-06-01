package org.cherchgk.domain;

import javax.persistence.*;

/**
 * Категория команды
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Entity
public class TeamCategory {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    public TeamCategory() {
    }

    public TeamCategory(String title, Tournament tournament) {
        this.title = title;
        this.tournament = tournament;
    }

    @PreRemove
    private void preRemove() {
        for (Team team : tournament.getTeams()) {
            if (this.equals(team.getTeamCategory())) {
                team.setTeamCategory(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamCategory category = (TeamCategory) o;
        return !(id != null ? !id.equals(category.id) : category.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
