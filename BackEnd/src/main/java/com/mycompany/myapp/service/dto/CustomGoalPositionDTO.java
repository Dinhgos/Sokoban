package com.mycompany.myapp.service.dto;

/**
 * A DTO representing GoalPositions, with its positions.
 */
public class CustomGoalPositionDTO {
    private Integer positionX;
    private Integer positionY;
    private Integer positionZ;

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public Integer getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(Integer positionZ) {
        this.positionZ = positionZ;
    }

    @Override
    public String toString() {
        return "CustomGoalPositionDTO{" +
            "positionX=" + positionX +
            ", positionY=" + positionY +
            ", positionZ=" + positionZ +
            '}';
    }
}
