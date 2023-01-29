package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.GoalPosition;
import com.mycompany.myapp.repository.GoalPositionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.GoalPosition}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GoalPositionResource extends CustomGoalPositionResource {

    private final Logger log = LoggerFactory.getLogger(GoalPositionResource.class);

    private static final String ENTITY_NAME = "goalPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoalPositionRepository goalPositionRepository;

    public GoalPositionResource(GoalPositionRepository goalPositionRepository) {
        this.goalPositionRepository = goalPositionRepository;
    }

    /**
     * {@code POST  /goal-positions} : Create a new goalPosition.
     *
     * @param goalPosition the goalPosition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goalPosition, or with status {@code 400 (Bad Request)} if the goalPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goal-positions")
    public ResponseEntity<GoalPosition> createGoalPosition(@RequestBody GoalPosition goalPosition) throws URISyntaxException {
        log.debug("REST request to save GoalPosition : {}", goalPosition);
        if (goalPosition.getId() != null) {
            throw new BadRequestAlertException("A new goalPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoalPosition result = goalPositionRepository.save(goalPosition);
        return ResponseEntity.created(new URI("/api/goal-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goal-positions} : Updates an existing goalPosition.
     *
     * @param goalPosition the goalPosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goalPosition,
     * or with status {@code 400 (Bad Request)} if the goalPosition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goalPosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goal-positions")
    public ResponseEntity<GoalPosition> updateGoalPosition(@RequestBody GoalPosition goalPosition) throws URISyntaxException {
        log.debug("REST request to update GoalPosition : {}", goalPosition);
        if (goalPosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoalPosition result = goalPositionRepository.save(goalPosition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, goalPosition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /goal-positions} : get all the goalPositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goalPositions in body.
     */
    @GetMapping("/goal-positions")
    public List<GoalPosition> getAllGoalPositions() {
        log.debug("REST request to get all GoalPositions");
        return goalPositionRepository.findAll();
    }

    /**
     * {@code GET  /goal-positions/:id} : get the "id" goalPosition.
     *
     * @param id the id of the goalPosition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goalPosition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goal-positions/{id}")
    public ResponseEntity<GoalPosition> getGoalPosition(@PathVariable Long id) {
        log.debug("REST request to get GoalPosition : {}", id);
        Optional<GoalPosition> goalPosition = goalPositionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(goalPosition);
    }

    /**
     * {@code DELETE  /goal-positions/:id} : delete the "id" goalPosition.
     *
     * @param id the id of the goalPosition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goal-positions/{id}")
    public ResponseEntity<Void> deleteGoalPosition(@PathVariable Long id) {
        log.debug("REST request to delete GoalPosition : {}", id);
        goalPositionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
