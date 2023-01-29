package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Boxes;
import com.mycompany.myapp.repository.BoxesRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Boxes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BoxesResource extends CustomBoxesResource {

    private final Logger log = LoggerFactory.getLogger(BoxesResource.class);

    private static final String ENTITY_NAME = "boxes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoxesRepository boxesRepository;

    public BoxesResource(BoxesRepository boxesRepository) {
        this.boxesRepository = boxesRepository;
    }

    /**
     * {@code POST  /boxes} : Create a new boxes.
     *
     * @param boxes the boxes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boxes, or with status {@code 400 (Bad Request)} if the boxes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boxes")
    public ResponseEntity<Boxes> createBoxes(@RequestBody Boxes boxes) throws URISyntaxException {
        log.debug("REST request to save Boxes : {}", boxes);
        if (boxes.getId() != null) {
            throw new BadRequestAlertException("A new boxes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Boxes result = boxesRepository.save(boxes);
        return ResponseEntity.created(new URI("/api/boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boxes} : Updates an existing boxes.
     *
     * @param boxes the boxes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boxes,
     * or with status {@code 400 (Bad Request)} if the boxes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boxes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boxes")
    public ResponseEntity<Boxes> updateBoxes(@RequestBody Boxes boxes) throws URISyntaxException {
        log.debug("REST request to update Boxes : {}", boxes);
        if (boxes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Boxes result = boxesRepository.save(boxes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, boxes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boxes} : get all the boxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boxes in body.
     */
    @GetMapping("/boxes")
    public List<Boxes> getAllBoxes() {
        log.debug("REST request to get all Boxes");
        return boxesRepository.findAll();
    }

    /**
     * {@code GET  /boxes/:id} : get the "id" boxes.
     *
     * @param id the id of the boxes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boxes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boxes/{id}")
    public ResponseEntity<Boxes> getBoxes(@PathVariable Long id) {
        log.debug("REST request to get Boxes : {}", id);
        Optional<Boxes> boxes = boxesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(boxes);
    }

    /**
     * {@code DELETE  /boxes/:id} : delete the "id" boxes.
     *
     * @param id the id of the boxes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boxes/{id}")
    public ResponseEntity<Void> deleteBoxes(@PathVariable Long id) {
        log.debug("REST request to delete Boxes : {}", id);
        boxesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
