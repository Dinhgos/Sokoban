package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Boxes;
import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.domain.Player;
import com.mycompany.myapp.domain.Save;
import com.mycompany.myapp.repository.SaveRepository;
import com.mycompany.myapp.service.CustomBoxesService;
import com.mycompany.myapp.service.dto.CustomBoxesDTO;
import com.mycompany.myapp.service.dto.CustomSaveDTO;
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
import java.util.Set;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Save}.
 */
public class CustomSaveResource {
    private final Logger log = LoggerFactory.getLogger(CustomSaveResource.class);

    private static final String ENTITY_NAME = "save";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private SaveResource saveResource;

    @Autowired
    private SaveRepository saveRepository;

    @Autowired
    private PlayerResource playerResource;

    @Autowired
    private MapResource mapResource;

    @Autowired
    private CustomBoxesService boxesService;

    @Autowired
    private BoxesResource boxesResource;

    /**
     * Najde save podle id hráće
     * @param id id hráće
     * @return seznam save
     */
    @GetMapping("/custom/save/byPlayerId/{id}")
    public List<Save> findSaveByPLayerId(@PathVariable Long id) {
        log.debug("REST request to get Save : {}", id);
        List<Save> save = saveRepository.findByPlayerId(id);

        if (save.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }

        return save;
    }

    /**
     * Najde save podle id mapy
     * @param id id mapy
     * @return seznam save
     */
    @GetMapping("/custom/save/byMapId/{id}")
    public List<Save> getMapsSaves(@PathVariable Long id) {
        log.debug("REST request to get Save : {}", id);
        List<Save> save = saveRepository.findByMapId(id);

        if (save.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }

        return save;
    }

    /**
     * Vytvoří save
     * @param saveDTO save
     * @return save
     * @throws URISyntaxException save
     */
    @PostMapping("/custom/createSave")
    public ResponseEntity<Save> createSave(@RequestBody CustomSaveDTO saveDTO) throws URISyntaxException {
        log.debug("REST request to post Save : {}", saveDTO);
        Save save = new Save();

        save.setMoves(saveDTO.getMoves());
        save.setTime(saveDTO.getTime());
        save.setPlayerPositionX(saveDTO.getPlayerPositionX());
        save.setPlayerPositionY(saveDTO.getPlayerPositionY());
        save.setPlayerPositionZ(saveDTO.getPlayerPositionZ());

        ResponseEntity<Player> player = playerResource.getPlayer(saveDTO.getFkPlayerId());
        save.setPlayer(player.getBody());

        ResponseEntity<Map> map = mapResource.getMap(saveDTO.getFkMapId());
        save.setMap(map.getBody());

        Save result = saveRepository.save(save);

        List<CustomBoxesDTO> boxes = saveDTO.getBoxes();
        for (CustomBoxesDTO b : boxes) {
            Boxes newBoxes = boxesService.convertToBoxes(b);
            newBoxes.setSave(save);
            boxesResource.createBoxes(newBoxes);
        }

        return ResponseEntity.created(new URI("/api/saves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * upravi save
     * @param saveDTO save
     * @return save
     * @throws URISyntaxException save
     */
    @PutMapping("/custom/updateSave")
    public ResponseEntity<Save> updateSave(@RequestBody CustomSaveDTO saveDTO) throws URISyntaxException {
        log.debug("REST request to put Save : {}", saveDTO);
        ResponseEntity<Save> save = saveResource.getSave(saveDTO.getId());

        save.getBody().setMoves(saveDTO.getMoves());
        save.getBody().setTime(saveDTO.getTime());
        save.getBody().setPlayerPositionX(saveDTO.getPlayerPositionX());
        save.getBody().setPlayerPositionY(saveDTO.getPlayerPositionY());
        save.getBody().setPlayerPositionZ(saveDTO.getPlayerPositionZ());

        ResponseEntity<Map> map = mapResource.getMap(saveDTO.getFkMapId());
        save.getBody().setMap(map.getBody());

        ResponseEntity<Player> player = playerResource.getPlayer(saveDTO.getFkPlayerId());
        save.getBody().setPlayer(player.getBody());

        List<Boxes> boxes = boxesResource.findBoxesBySaveId(saveDTO.getId());

        for (int i = 0; i < boxes.size(); i++) {
            boxes.get(i).setPositionX(saveDTO.getBoxes().get(i).getPositionX());
            boxes.get(i).setPositionY(saveDTO.getBoxes().get(i).getPositionY());
            boxes.get(i).setPositionZ(saveDTO.getBoxes().get(i).getPositionZ());

            boxesResource.updateBoxes(boxes.get(i));
        }

        Save result = saveRepository.save(save.getBody());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, save.getBody().getId().toString()))
            .body(result);
    }

    /**
     * Vymaže save podle id hráče
     * @param id id hráče
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/custom/deleteSave/{id}")
    public ResponseEntity<Void> deleteSaveByPlayerId(@PathVariable Long id) {
        log.debug("REST request to delete Save : {}", id);
        Save save = saveResource.findSaveByPLayerId(id).get(0);
        saveRepository.deleteById(save.getId());
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
