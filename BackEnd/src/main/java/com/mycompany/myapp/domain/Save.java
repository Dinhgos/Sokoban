package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Save.
 */
@Entity
@Table(name = "save")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Save implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moves")
    private Integer moves;

    @Column(name = "time")
    private Integer time;

    @Column(name = "player_position_x")
    private Integer playerPositionX;

    @Column(name = "player_position_y")
    private Integer playerPositionY;

    @Column(name = "player_position_z")
    private Integer playerPositionZ;

    @OneToMany(mappedBy = "save")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Boxes> boxes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "saves", allowSetters = true)
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties(value = "saves", allowSetters = true)
    private Map map;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMoves() {
        return moves;
    }

    public Save moves(Integer moves) {
        this.moves = moves;
        return this;
    }

    public void setMoves(Integer moves) {
        this.moves = moves;
    }

    public Integer getTime() {
        return time;
    }

    public Save time(Integer time) {
        this.time = time;
        return this;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getPlayerPositionX() {
        return playerPositionX;
    }

    public Save playerPositionX(Integer playerPositionX) {
        this.playerPositionX = playerPositionX;
        return this;
    }

    public void setPlayerPositionX(Integer playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    public Integer getPlayerPositionY() {
        return playerPositionY;
    }

    public Save playerPositionY(Integer playerPositionY) {
        this.playerPositionY = playerPositionY;
        return this;
    }

    public void setPlayerPositionY(Integer playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    public Integer getPlayerPositionZ() {
        return playerPositionZ;
    }

    public Save playerPositionZ(Integer playerPositionZ) {
        this.playerPositionZ = playerPositionZ;
        return this;
    }

    public void setPlayerPositionZ(Integer playerPositionZ) {
        this.playerPositionZ = playerPositionZ;
    }

    public Set<Boxes> getBoxes() {
        return boxes;
    }

    public Save boxes(Set<Boxes> boxes) {
        this.boxes = boxes;
        return this;
    }

    public Save addBoxes(Boxes boxes) {
        this.boxes.add(boxes);
        boxes.setSave(this);
        return this;
    }

    public Save removeBoxes(Boxes boxes) {
        this.boxes.remove(boxes);
        boxes.setSave(null);
        return this;
    }

    public void setBoxes(Set<Boxes> boxes) {
        this.boxes = boxes;
    }

    public Player getPlayer() {
        return player;
    }

    public Save player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map getMap() {
        return map;
    }

    public Save map(Map map) {
        this.map = map;
        return this;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Save)) {
            return false;
        }
        return id != null && id.equals(((Save) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Save{" +
            "id=" + getId() +
            ", moves=" + getMoves() +
            ", time=" + getTime() +
            ", playerPositionX=" + getPlayerPositionX() +
            ", playerPositionY=" + getPlayerPositionY() +
            ", playerPositionZ=" + getPlayerPositionZ() +
            "}";
    }
}
