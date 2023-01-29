package com.mycompany.myapp.service.dto;

/**
 * A DTO representing Walls, with its positions.
 */
public class CustomWallsDTO {
    private Integer positionX;
    private Integer positionY;
    private Integer positionZ;

    public Integer getPositionX() {
        return positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public Integer getPositionZ() {
        return positionZ;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public void setPositionZ(Integer positionZ) {
        this.positionZ = positionZ;
    }

    @Override
    public String toString() {
        return "CustomWallsDTO{" +
            "positionX=" + positionX +
            ", positionY=" + positionY +
            ", positionZ=" + positionZ +
            '}';
    }
}
