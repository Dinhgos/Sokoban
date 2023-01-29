package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Player;
import com.mycompany.myapp.domain.Score;
import com.mycompany.myapp.repository.PlayerRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Player}.
 */
public class CustomPlayerResource {
    private static final String ENTITY_NAME = "player";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerResource playerResource;

    /**
     * Najde hráče podle jména
     * @param name jméno hráče
     * @return Player
     */
    @GetMapping("/player/{name}")
    public ResponseEntity<Player> findPlayerByName(@PathVariable String name) {
        Optional<Player> player = playerRepository.findByName(name);
        return ResponseUtil.wrapOrNotFound(player);
    }

    /**
     * Upraví hráče
     * @param playerId id hráče
     * @param value hodnota level
     * @return Player
     * @throws URISyntaxException hráč nenalezen
     */
    @PutMapping("/custom/player")
    public ResponseEntity<Player> updatePlayer(
        @RequestParam("playerId") Long playerId, @RequestParam("value") Integer value
    ) throws URISyntaxException {
        ResponseEntity<Player> player = playerResource.getPlayer(playerId);
        player.getBody().setLevel(value);
        Player result = playerRepository.save(player.getBody());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, player.getBody().getId().toString()))
            .body(result);
    }
}
