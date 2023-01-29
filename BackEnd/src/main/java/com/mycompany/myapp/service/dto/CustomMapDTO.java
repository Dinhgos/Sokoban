package com.mycompany.myapp.service.dto;

import java.util.List;

/**
 * A DTO representing Map, with everything.
 */
public class CustomMapDTO {
    private Long mapId;
    private Integer playerPositionX;
    private Integer playerPositionY;
    private Integer playerPositionZ;
    private List<CustomGoalPositionDTO> goalPositions;
    private List<CustomWallsDTO> walls;
    private List<CustomBoxesDTO> boxes;

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public Integer getPlayerPositionX() {
        return playerPositionX;
    }

    public void setPlayerPositionX(Integer playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    public Integer getPlayerPositionY() {
        return playerPositionY;
    }

    public void setPlayerPositionY(Integer playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    public Integer getPlayerPositionZ() {
        return playerPositionZ;
    }

    public void setPlayerPositionZ(Integer playerPositionZ) {
        this.playerPositionZ = playerPositionZ;
    }

    public List<CustomGoalPositionDTO> getGoalPositions() {
        return goalPositions;
    }

    public void setGoalPositions(List<CustomGoalPositionDTO> goalPositions) {
        this.goalPositions = goalPositions;
    }

    public List<CustomWallsDTO> getWalls() {
        return walls;
    }

    public void setWalls(List<CustomWallsDTO> walls) {
        this.walls = walls;
    }

    public List<CustomBoxesDTO> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<CustomBoxesDTO> boxes) {
        this.boxes = boxes;
    }

    @Override
    public String toString() {
        return "CustomMapDTO{" +
            "mapId=" + getMapId() +
            ", playerPositionX=" + getPlayerPositionX() +
            ", playerPositionY=" + getPlayerPositionY() +
            ", playerPositionZ=" + getPlayerPositionZ() +
            ", goalPositions=" + getGoalPositions() +
            ", walls=" + getWalls() +
            ", boxes=" + getBoxes() +
            '}';
    }
}
