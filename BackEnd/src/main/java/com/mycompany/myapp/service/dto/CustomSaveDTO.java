package com.mycompany.myapp.service.dto;

import java.util.List;

/**
 * A DTO representing Save.
 */
public class CustomSaveDTO {
    private Long id;
    private Integer moves;
    private Integer time;
    private Integer playerPositionX;
    private Integer playerPositionY;
    private Integer playerPositionZ;
    private List<CustomBoxesDTO> boxes;
    private Long fkPlayerId;
    private Long fkMapId;

    public Integer getMoves() {
        return moves;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoves(Integer moves) {
        this.moves = moves;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
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

    public List<CustomBoxesDTO> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<CustomBoxesDTO> boxes) {
        this.boxes = boxes;
    }

    public Long getFkPlayerId() {
        return fkPlayerId;
    }

    public void setFkPlayerId(Long fkPlayerId) {
        this.fkPlayerId = fkPlayerId;
    }

    public Long getFkMapId() {
        return fkMapId;
    }

    public void setFkMapId(Long fkMapId) {
        this.fkMapId = fkMapId;
    }

    @Override
    public String toString() {
        return "CustomSaveDTO{" +
            "moves=" + moves +
            ", time=" + time +
            ", playerPositionX=" + playerPositionX +
            ", playerPositionY=" + playerPositionY +
            ", playerPositionZ=" + playerPositionZ +
            ", boxes=" + boxes +
            ", fkPlayerId=" + fkPlayerId +
            ", fkMapId=" + fkMapId +
            '}';
    }
}
