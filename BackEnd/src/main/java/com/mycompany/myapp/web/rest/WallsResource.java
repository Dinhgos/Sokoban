package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Walls;
import com.mycompany.myapp.repository.WallsRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Walls}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WallsResource {

    private final Logger log = LoggerFactory.getLogger(WallsResource.class);

    private static final String ENTITY_NAME = "walls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WallsRepository wallsRepository;

    public WallsResource(WallsRepository wallsRepository) {
        this.wallsRepository = wallsRepository;
    }

    /**
     * {@code POST  /walls} : Create a new walls.
     *
     * @param walls the walls to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new walls, or with status {@code 400 (Bad Request)} if the walls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/walls")
    public ResponseEntity<Walls> createWalls(@RequestBody Walls walls) throws URISyntaxException {
        log.debug("REST request to save Walls : {}", walls);
        if (walls.getId() != null) {
            throw new BadRequestAlertException("A new walls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Walls result = wallsRepository.save(walls);
        return ResponseEntity.created(new URI("/api/walls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /walls} : Updates an existing walls.
     *
     * @param walls the walls to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated walls,
     * or with status {@code 400 (Bad Request)} if the walls is not valid,
     * or with status {@code 500 (Internal Server Error)} if the walls couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/walls")
    public ResponseEntity<Walls> updateWalls(@RequestBody Walls walls) throws URISyntaxException {
        log.debug("REST request to update Walls : {}", walls);
        if (walls.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Walls result = wallsRepository.save(walls);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, walls.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /walls} : get all the walls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of walls in body.
     */
    @GetMapping("/walls")
    public List<Walls> getAllWalls() {
        log.debug("REST request to get all Walls");
        return wallsRepository.findAll();
    }

    /**
     * {@code GET  /walls/:id} : get the "id" walls.
     *
     * @param id the id of the walls to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the walls, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/walls/{id}")
    public ResponseEntity<Walls> getWalls(@PathVariable Long id) {
        log.debug("REST request to get Walls : {}", id);
        Optional<Walls> walls = wallsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(walls);
    }

    /**
     * {@code DELETE  /walls/:id} : delete the "id" walls.
     *
     * @param id the id of the walls to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/walls/{id}")
    public ResponseEntity<Void> deleteWalls(@PathVariable Long id) {
        log.debug("REST request to delete Walls : {}", id);
        wallsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
