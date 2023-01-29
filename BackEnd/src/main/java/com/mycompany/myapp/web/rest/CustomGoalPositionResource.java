package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CustomGoalPositionService;
import com.mycompany.myapp.service.dto.CustomGoalPositionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.GoalPosition}.
 */
public class CustomGoalPositionResource {
    private final Logger log = LoggerFactory.getLogger(CustomGoalPositionResource.class);

    @Autowired
    private CustomGoalPositionService customGoalPositionService;

    /**
     * Najde všechny krabice podle id mapy
     * @param id id mapy
     * @return seznam CustomBoxesDTO
     */
    @GetMapping("/custom/goalPosition/{id}")
    public List<CustomGoalPositionDTO> findGoalPositionByMapId(@PathVariable Long id) {
        log.debug("REST request to get CustomGoalPositionDTO : {}", id);
        List<CustomGoalPositionDTO> tmp = customGoalPositionService.getAllMapsGoalPositions(id);

        if (tmp.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }

        return tmp;
    }
}
