package org.cherchgk.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Турнир
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Entity
public class Tournament implements DomainObject {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Date date;
    private Integer questionAmount;
    @OneToMany
    @JoinColumn(name = "tournament_id")
    @OrderBy("number")
    private List<Team> teams;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(Integer questionAmount) {
        this.questionAmount = questionAmount;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
