package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.domain.Player;
import com.mycompany.myapp.domain.Score;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.repository.PlayerRepository;
import com.mycompany.myapp.repository.ScoreRepository;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Score}.
 */
public class CustomScoreResource {

    private final Logger log = LoggerFactory.getLogger(CustomScoreResource.class);
    private static final String ENTITY_NAME = "score";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    /**
     * Vytvoří score
     * @param playerID id hráče
     * @param mapID id mapy
     * @param value hodnota score
     * @return Score
     * @throws URISyntaxException chyba
     */
    @PostMapping("/custom/createCustomScore")
    public ResponseEntity<Score> createCustomScore(
        @RequestParam("playerID") Long playerID, @RequestParam("mapID") Long mapID, @RequestParam("value") Integer value
    ) throws URISyntaxException {
        Score score = new Score();

        Player player = playerRepository.findByID(playerID);
        score.setPlayer(player);

        Optional<Map> optionalMap = mapRepository.findByMapId(mapID);
        ResponseEntity<Map> map = ResponseUtil.wrapOrNotFound(optionalMap);
        score.setMap(map.getBody());

        score.setValue(value);

        Instant date = Instant.now();
        score.setDate(date);

        Score result = scoreRepository.save(score);
        return ResponseEntity.created(new URI("/api/scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * Upraví score
     * @param value hodnota score
     * @param scoreID id score
     * @return Score
     * @throws URISyntaxException chyba
     */
    @PutMapping("/custom/updateScore")
    public ResponseEntity<Score> updateScore(
        @RequestParam("value") Integer value, @RequestParam("scoreID") Long scoreID
    ) throws URISyntaxException {
        Optional<Score> optionalScore = scoreRepository.findById(scoreID);
        ResponseEntity<Score> score = ResponseUtil.wrapOrNotFound(optionalScore);

        score.getBody().setValue(value);
        Instant date = Instant.now();
        score.getBody().setDate(date);

        Score result = scoreRepository.save(score.getBody());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, score.getBody().getId().toString()))
            .body(result);
    }

    /**
     * Najde score podle id mapy
     * @param id id mapy
     * @return seznam Score
     */
    @GetMapping("/custom/byMapId/{id}")
    public List<Score> findMapsScores(@PathVariable Integer id) {
        List<Score> tmp = scoreRepository.findByMapId(id);

        if (tmp.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }

        return tmp;
    }

    /**
     * Najde score podle id hráće
     * @param id id hráće
     * @return Score
     */
    @GetMapping("/score/{id}")
    public List<Score> findScoreByPlayerID(@PathVariable Integer id) {
        List<Score> tmp = scoreRepository.findByPlayerId(id);

        if (tmp.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }

        return tmp;
    }
}
