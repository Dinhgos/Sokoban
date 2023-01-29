package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.domain.Player;
import com.mycompany.myapp.domain.Save;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.repository.PlayerRepository;
import com.mycompany.myapp.repository.SaveRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Save}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SaveResource extends CustomSaveResource {

    private final Logger log = LoggerFactory.getLogger(SaveResource.class);

    private static final String ENTITY_NAME = "save";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaveRepository saveRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MapRepository mapRepository;

    public SaveResource(SaveRepository saveRepository) {
        this.saveRepository = saveRepository;
    }

    /**
     * {@code POST  /saves} : Create a new save.
     *
     * @param save the save to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new save, or with status {@code 400 (Bad Request)} if the save has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/saves")
    public ResponseEntity<Save> createSave(@RequestBody Save save) throws URISyntaxException {
        log.debug("REST request to save Save : {}", save);
        if (save.getId() != null) {
            throw new BadRequestAlertException("A new save cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Save result = saveRepository.save(save);
        return ResponseEntity.created(new URI("/api/saves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saves} : Updates an existing save.
     *
     * @param save the save to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated save,
     * or with status {@code 400 (Bad Request)} if the save is not valid,
     * or with status {@code 500 (Internal Server Error)} if the save couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/saves")
    public ResponseEntity<Save> updateSave(@RequestBody Save save) throws URISyntaxException {
        log.debug("REST request to update Save : {}", save);
        if (save.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Save result = saveRepository.save(save);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, save.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /saves} : get all the saves.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saves in body.
     */
    @GetMapping("/saves")
    public List<Save> getAllSaves() {
        log.debug("REST request to get all Saves");
        return saveRepository.findAll();
    }

    /**
     * {@code GET  /saves/:id} : get the "id" save.
     *
     * @param id the id of the save to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the save, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/saves/{id}")
    public ResponseEntity<Save> getSave(@PathVariable Long id) {
        log.debug("REST request to get Save : {}", id);
        Optional<Save> save = saveRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(save);
    }

    /**
     * {@code DELETE  /saves/:id} : delete the "id" save.
     *
     * @param id the id of the save to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/saves/{id}")
    public ResponseEntity<Void> deleteSave(@PathVariable Long id) {
        log.debug("REST request to delete Save : {}", id);
        saveRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
