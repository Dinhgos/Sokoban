package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Boxes;
import com.mycompany.myapp.repository.BoxesRepository;
import com.mycompany.myapp.service.CustomBoxesService;
import com.mycompany.myapp.service.dto.CustomBoxesDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Boxes}.
 */
public class CustomBoxesResource {
    private final Logger log = LoggerFactory.getLogger(CustomBoxesResource.class);

    private static final String ENTITY_NAME = "boxes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private CustomBoxesService customBoxesService;

    @Autowired
    private BoxesRepository boxesRepository;

    @Autowired
    private BoxesResource boxesResource;

    /**
     * Najde všechny krabice podle id mapy
     * @param id id mapy
     * @return seznam CustomBoxesDTO
     */
    @GetMapping("/custom/boxes/{id}")
    public List<CustomBoxesDTO> findBoxesByMapId(@PathVariable Long id) {
        log.debug("REST request to get CustomBoxesDTO : {}", id);
        List<CustomBoxesDTO> tmp = customBoxesService.getAllMapsBoxes(id);

        if (tmp.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }

        return tmp;
    }

    /**
     * Najde všechny krabice podle id save
     * @param id id save
     * @return seznam CustomBoxesDTO
     */
    @GetMapping("/custom/boxes/bySaveId/{id}")
    public List<Boxes> findBoxesBySaveId(@PathVariable Long id) {
        log.debug("REST request to get Boxes : {}", id);
        List<Boxes> tmp = boxesRepository.findBySaveId(id);

        if (tmp.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }

        return tmp;
    }

    /**
     * Smaže všechny krabice podle id save
     * @param id id save
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom/deleteBoxes/{id}")
    public ResponseEntity<Void> deleteBoxesBySaveId(@PathVariable Long id) {
        log.debug("REST request to delete Boxes : {}", id);
        List<Boxes> boxesList = boxesRepository.findBySaveId(id);

        for (Boxes b : boxesList) {
            boxesRepository.deleteById(b.getId());
        }

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
