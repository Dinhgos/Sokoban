package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "level")
    private Integer level;

    @OneToMany(mappedBy = "player")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Score> scores = new HashSet<>();

    @OneToMany(mappedBy = "player")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Save> saves = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Player name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Player password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public Player level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Player scores(Set<Score> scores) {
        this.scores = scores;
        return this;
    }

    public Player addScore(Score score) {
        this.scores.add(score);
        score.setPlayer(this);
        return this;
    }

    public Player removeScore(Score score) {
        this.scores.remove(score);
        score.setPlayer(null);
        return this;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<Save> getSaves() {
        return saves;
    }

    public Player saves(Set<Save> saves) {
        this.saves = saves;
        return this;
    }

    public Player addSave(Save save) {
        this.saves.add(save);
        save.setPlayer(this);
        return this;
    }

    public Player removeSave(Save save) {
        this.saves.remove(save);
        save.setPlayer(null);
        return this;
    }

    public void setSaves(Set<Save> saves) {
        this.saves = saves;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", password='" + getPassword() + "'" +
            ", level=" + getLevel() +
            "}";
    }
}
