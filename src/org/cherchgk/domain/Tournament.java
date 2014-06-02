package org.cherchgk.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Турнир
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tournament implements DomainObject {

    /**
     * Способы нумерации вопросов в туре.
     */
    public enum QuestionNumberingType {
        SEQUENTIALLY("Сквозная"),
        ROUND("Потуровая");

        private String title;

        private QuestionNumberingType(String title) {
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
    private String title;
    private Date date;
    private Integer questionAmount;
    private Integer roundAmount;
    @Enumerated(EnumType.STRING)
    private QuestionNumberingType questionNumberingType;
    @OneToMany
    @JoinColumn(name = "tournament_id")
    @OrderBy("number")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Team> teams;
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("title")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<TeamCategory> teamCategories;

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
        return (date != null) ? new Date(date.getTime()) : null;
    }

    public void setDate(Date date) {
        this.date = (date != null) ? new Date(date.getTime()) : null;
    }

    public String getDateAsString() {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public Integer getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(Integer questionAmount) {
        this.questionAmount = questionAmount;
    }

    public Integer getRoundAmount() {
        return roundAmount;
    }

    public void setRoundAmount(Integer roundAmount) {
        this.roundAmount = roundAmount;
    }

    public QuestionNumberingType getQuestionNumberingType() {
        return questionNumberingType;
    }

    public void setQuestionNumberingType(QuestionNumberingType questionNumberingType) {
        this.questionNumberingType = questionNumberingType;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<TeamCategory> getTeamCategories() {
        return teamCategories;
    }

    public void setTeamCategories(List<TeamCategory> teamCategories) {
        this.teamCategories = teamCategories;
    }

    /**
     * Получить информацию о категориях команд, разрешённых на турнире
     * в виде ассоциативного массива (ключ - идентификатор категории;
     * значение - название категории).
     *
     * @return ассоциативный массив с информацией о категориях, разрешённых
     * на турнире.
     */
    public Map<Long, String> getTeamCategoriesMap() {
        Map<Long, String> teamCategories = new LinkedHashMap<Long, String>();
        for (TeamCategory category : getTeamCategories()) {
            teamCategories.put(category.getId(), category.getTitle());
        }
        return teamCategories;
    }
}
