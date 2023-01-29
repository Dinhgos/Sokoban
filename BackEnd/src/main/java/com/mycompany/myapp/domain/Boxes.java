package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Boxes.
 */
@Entity
@Table(name = "boxes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Boxes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_x")
    private Integer positionX;

    @Column(name = "position_y")
    private Integer positionY;

    @Column(name = "position_z")
    private Integer positionZ;

    @ManyToOne
    @JsonIgnoreProperties(value = "boxes", allowSetters = true)
    private Save save;

    @ManyToOne
    @JsonIgnoreProperties(value = "boxes", allowSetters = true)
    private Map map;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public Boxes positionX(Integer positionX) {
        this.positionX = positionX;
        return this;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public Boxes positionY(Integer positionY) {
        this.positionY = positionY;
        return this;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public Integer getPositionZ() {
        return positionZ;
    }

    public Boxes positionZ(Integer positionZ) {
        this.positionZ = positionZ;
        return this;
    }

    public void setPositionZ(Integer positionZ) {
        this.positionZ = positionZ;
    }

    public Save getSave() {
        return save;
    }

    public Boxes save(Save save) {
        this.save = save;
        return this;
    }

    public void setSave(Save save) {
        this.save = save;
    }

    public Map getMap() {
        return map;
    }

    public Boxes map(Map map) {
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
        if (!(o instanceof Boxes)) {
            return false;
        }
        return id != null && id.equals(((Boxes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Boxes{" +
            "id=" + getId() +
            ", positionX=" + getPositionX() +
            ", positionY=" + getPositionY() +
            ", positionZ=" + getPositionZ() +
            "}";
    }
}
