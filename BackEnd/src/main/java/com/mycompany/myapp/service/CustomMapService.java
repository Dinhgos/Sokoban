package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.repository.BoxesRepository;
import com.mycompany.myapp.repository.GoalPositionRepository;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.repository.WallsRepository;
import com.mycompany.myapp.service.dto.CustomBoxesDTO;
import com.mycompany.myapp.service.dto.CustomGoalPositionDTO;
import com.mycompany.myapp.service.dto.CustomMapDTO;
import com.mycompany.myapp.service.dto.CustomWallsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Map.
 */
@Service
public class CustomMapService {
    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private GoalPositionRepository goalPositionRepository;

    @Autowired
    private WallsRepository wallsRepository;

    @Autowired
    private BoxesRepository boxesRepository;

    @Autowired
    private CustomGoalPositionService customGoalPositionService;

    @Autowired
    private CustomWallsService customWallsService;

    @Autowired
    private CustomBoxesService customBoxesService;

    /**
     * Vytvoří CustomMapDTO pomocí id mapy
     * @param id id mapy
     * @return CustomMapDTO
     */
    public CustomMapDTO convertToCustomMapDTO(Long id) {
        CustomMapDTO customMapDTO = new CustomMapDTO();
        customMapDTO.setMapId(id);

        Optional<Map> optionalMap = mapRepository.findByMapId(id);
        ResponseEntity<Map> map = ResponseUtil.wrapOrNotFound(optionalMap);

        customMapDTO.setPlayerPositionX(map.getBody().getPlayerPositionX());
        customMapDTO.setPlayerPositionY(map.getBody().getPlayerPositionY());
        customMapDTO.setPlayerPositionZ(map.getBody().getPlayerPositionZ());

        List<CustomGoalPositionDTO> goals = customGoalPositionService.getAllMapsGoalPositions(id);
        customMapDTO.setGoalPositions(goals);

        List<CustomWallsDTO> walls = customWallsService.getAllMapsWalls(id);
        customMapDTO.setWalls(walls);

        List<CustomBoxesDTO> boxes = customBoxesService.getAllMapsBoxes(id);
        customMapDTO.setBoxes(boxes);

        return customMapDTO;
    }
}
