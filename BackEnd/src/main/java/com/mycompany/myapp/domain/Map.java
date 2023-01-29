package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Map.
 */
@Entity
@Table(name = "map")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Map implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_position_x")
    private Integer playerPositionX;

    @Column(name = "player_position_y")
    private Integer playerPositionY;

    @Column(name = "player_position_z")
    private Integer playerPositionZ;

    @OneToMany(mappedBy = "map")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Score> scores = new HashSet<>();

    @OneToMany(mappedBy = "map")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Save> saves = new HashSet<>();

    @OneToMany(mappedBy = "map")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GoalPosition> goalPositions = new HashSet<>();

    @OneToMany(mappedBy = "map")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Walls> walls = new HashSet<>();

    @OneToMany(mappedBy = "map")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Boxes> boxes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlayerPositionX() {
        return playerPositionX;
    }

    public Map playerPositionX(Integer playerPositionX) {
        this.playerPositionX = playerPositionX;
        return this;
    }

    public void setPlayerPositionX(Integer playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    public Integer getPlayerPositionY() {
        return playerPositionY;
    }

    public Map playerPositionY(Integer playerPositionY) {
        this.playerPositionY = playerPositionY;
        return this;
    }

    public void setPlayerPositionY(Integer playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    public Integer getPlayerPositionZ() {
        return playerPositionZ;
    }

    public Map playerPositionZ(Integer playerPositionZ) {
        this.playerPositionZ = playerPositionZ;
        return this;
    }

    public void setPlayerPositionZ(Integer playerPositionZ) {
        this.playerPositionZ = playerPositionZ;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Map scores(Set<Score> scores) {
        this.scores = scores;
        return this;
    }

    public Map addScore(Score score) {
        this.scores.add(score);
        score.setMap(this);
        return this;
    }

    public Map removeScore(Score score) {
        this.scores.remove(score);
        score.setMap(null);
        return this;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<Save> getSaves() {
        return saves;
    }

    public Map saves(Set<Save> saves) {
        this.saves = saves;
        return this;
    }

    public Map addSave(Save save) {
        this.saves.add(save);
        save.setMap(this);
        return this;
    }

    public Map removeSave(Save save) {
        this.saves.remove(save);
        save.setMap(null);
        return this;
    }

    public void setSaves(Set<Save> saves) {
        this.saves = saves;
    }

    public Set<GoalPosition> getGoalPositions() {
        return goalPositions;
    }

    public Map goalPositions(Set<GoalPosition> goalPositions) {
        this.goalPositions = goalPositions;
        return this;
    }

    public Map addGoalPosition(GoalPosition goalPosition) {
        this.goalPositions.add(goalPosition);
        goalPosition.setMap(this);
        return this;
    }

    public Map removeGoalPosition(GoalPosition goalPosition) {
        this.goalPositions.remove(goalPosition);
        goalPosition.setMap(null);
        return this;
    }

    public void setGoalPositions(Set<GoalPosition> goalPositions) {
        this.goalPositions = goalPositions;
    }

    public Set<Walls> getWalls() {
        return walls;
    }

    public Map walls(Set<Walls> walls) {
        this.walls = walls;
        return this;
    }

    public Map addWalls(Walls walls) {
        this.walls.add(walls);
        walls.setMap(this);
        return this;
    }

    public Map removeWalls(Walls walls) {
        this.walls.remove(walls);
        walls.setMap(null);
        return this;
    }

    public void setWalls(Set<Walls> walls) {
        this.walls = walls;
    }

    public Set<Boxes> getBoxes() {
        return boxes;
    }

    public Map boxes(Set<Boxes> boxes) {
        this.boxes = boxes;
        return this;
    }

    public Map addBoxes(Boxes boxes) {
        this.boxes.add(boxes);
        boxes.setMap(this);
        return this;
    }

    public Map removeBoxes(Boxes boxes) {
        this.boxes.remove(boxes);
        boxes.setMap(null);
        return this;
    }

    public void setBoxes(Set<Boxes> boxes) {
        this.boxes = boxes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        return id != null && id.equals(((Map) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Map{" +
            "id=" + getId() +
            ", playerPositionX=" + getPlayerPositionX() +
            ", playerPositionY=" + getPlayerPositionY() +
            ", playerPositionZ=" + getPlayerPositionZ() +
            "}";
    }
}
