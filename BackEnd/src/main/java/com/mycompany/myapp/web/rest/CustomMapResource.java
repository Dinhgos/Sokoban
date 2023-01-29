package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Boxes;
import com.mycompany.myapp.domain.GoalPosition;
import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.domain.Walls;
import com.mycompany.myapp.repository.BoxesRepository;
import com.mycompany.myapp.repository.GoalPositionRepository;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.repository.WallsRepository;
import com.mycompany.myapp.service.CustomBoxesService;
import com.mycompany.myapp.service.CustomGoalPositionService;
import com.mycompany.myapp.service.CustomMapService;
import com.mycompany.myapp.service.CustomWallsService;
import com.mycompany.myapp.service.dto.CustomBoxesDTO;
import com.mycompany.myapp.service.dto.CustomGoalPositionDTO;
import com.mycompany.myapp.service.dto.CustomMapDTO;
import com.mycompany.myapp.service.dto.CustomWallsDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Map}.
 */
public class CustomMapResource {
    private final Logger log = LoggerFactory.getLogger(CustomMapResource.class);

    private static final String ENTITY_NAME = "map";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private CustomMapService customMapService;

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

    @Autowired
    private GoalPositionResource goalPositionResource;

    @Autowired
    private WallsResource wallsResource;

    @Autowired
    private MapResource mapResource;

    @Autowired
    private BoxesResource boxesResource;

    /**
     * Najde mapu podle id
     * @param id id mapy
     * @return CustomMapDTO
     */
    @GetMapping("/custom/map/{id}")
    public ResponseEntity<CustomMapDTO> findMapById(@PathVariable Long id) {
        log.debug("REST request to get CustomMapDTO : {}", id);

        Optional<CustomMapDTO> tmp = Optional.ofNullable(customMapService.convertToCustomMapDTO(id));
        return ResponseUtil.wrapOrNotFound(tmp);
    }

    /**
     * Uloží mapu
     * @param mapDTO CustomMapDTO
     * @return Map
     */
    @PostMapping("/custom/map")
    public ResponseEntity<Map> createCustomMap(@RequestBody CustomMapDTO mapDTO) throws URISyntaxException {
        log.debug("REST request to save CustomMapDTO : {}", mapDTO);
        Map map = new Map();

        map.setPlayerPositionX(mapDTO.getPlayerPositionX());
        map.setPlayerPositionY(mapDTO.getPlayerPositionY());
        map.setPlayerPositionZ(mapDTO.getPlayerPositionZ());

        mapResource.createMap(map);

        for (CustomGoalPositionDTO tmp : mapDTO.getGoalPositions()) {
            GoalPosition newGp = customGoalPositionService.convertToGoalPosition(tmp);
            newGp.setMap(map);
            goalPositionResource.createGoalPosition(newGp);
        }

        for (CustomWallsDTO tmp : mapDTO.getWalls()) {
            Walls newWalls = customWallsService.convertToWalls(tmp);
            newWalls.setMap(map);
            wallsResource.createWalls(newWalls);
        }

        for (CustomBoxesDTO tmp : mapDTO.getBoxes()) {
            Boxes newBoxes = customBoxesService.convertToBoxes(tmp);
            newBoxes.setMap(map);
            boxesResource.createBoxes(newBoxes);
        }

        return mapResource.getMap(map.getId());
    }
}
