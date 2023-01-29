package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Walls;
import com.mycompany.myapp.repository.WallsRepository;
import com.mycompany.myapp.service.dto.CustomWallsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Walls.
 */
@Service
public class CustomWallsService {

    @Autowired
    private WallsRepository wallsRepository;

    /**
     * Najde všechny zdi podle id mapy
     * @param id id mapy
     * @return seznam CustomWallsDTO
     */
    public List<CustomWallsDTO> getAllMapsWalls(Long id) {
        return wallsRepository
            .findByMapId(id)
            .stream()
            .map(this::convertToGoalPosition)
            .collect(Collectors.toList());
    }

    /**
     * Převede CustomWallsDTO na Walls
     * @param cw CustomWallsDTO
     * @return Walls
     */
    public Walls convertToWalls(CustomWallsDTO cw) {
        Walls tmp = new Walls();

        tmp.setPositionX(cw.getPositionX());
        tmp.setPositionY(cw.getPositionY());
        tmp.setPositionZ(cw.getPositionZ());

        return tmp;
    }

    /**
     * Převede Walls na CustomWallsDTO
     * @param walls Walls
     * @return CustomWallsDTO
     */
    private CustomWallsDTO convertToGoalPosition(Walls walls) {
        CustomWallsDTO tmp = new CustomWallsDTO();

        tmp.setPositionX(walls.getPositionX());
        tmp.setPositionY(walls.getPositionY());
        tmp.setPositionZ(walls.getPositionZ());

        return tmp;
    }
}
