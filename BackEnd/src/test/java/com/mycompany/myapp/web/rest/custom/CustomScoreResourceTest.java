package com.mycompany.myapp.web.rest.custom;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.domain.Player;
import com.mycompany.myapp.domain.Score;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.repository.PlayerRepository;
import com.mycompany.myapp.repository.ScoreRepository;
import com.mycompany.myapp.web.rest.ScoreResource;
import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomScoreResourceTest {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScoreMockMvc;

    @Autowired
    private ScoreResource scoreResource;

    private Score score;

    @BeforeEach
    public void initTest() {
        score = new Score()
            .value(1)
            .date(Instant.parse("2018-11-30T18:35:24.00Z"));
    }

    @Test
    void createCustomScore() throws Exception {
        Player player = new Player();
        Map map = new Map();

        playerRepository.saveAndFlush(player);
        mapRepository.saveAndFlush(map);

        ResponseEntity<Score> result = scoreResource.createCustomScore(player.getId(),map.getId(),1);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(1, result.getBody().getValue());
    }

    @Test
    void updateScore_1() throws URISyntaxException {
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        ResponseEntity<Score> result = scoreResource.updateScore(2, score.getId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().getValue());

        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.getValue()).isEqualTo(2);
    }

    @Test
    void updateScore_2() throws Exception {
        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreMockMvc.perform(put("/api/custom/updateScore")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(score)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void findMapsScores_1() {
        Map map = new Map();
        score.setMap(map);

        mapRepository.saveAndFlush(map);
        scoreRepository.saveAndFlush(score);

        List<Score> result = scoreResource.findMapsScores(map.getId().intValue());
        assertEquals(map, result.get(0).getMap());
        assertEquals(1, result.get(0).getValue());
        assertEquals(Instant.parse("2018-11-30T18:35:24.00Z"), result.get(0).getDate());
    }

    @Test
    void findMapsScores_2() throws Exception {
        restScoreMockMvc.perform(get("/api/custom/byMapId/{id}", Integer.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    void findScoreByPlayerID_1() {
        Player player = new Player();
        score.setPlayer(player);

        playerRepository.saveAndFlush(player);
        scoreRepository.saveAndFlush(score);

        List<Score> result = scoreResource.findScoreByPlayerID(player.getId().intValue());
        assertEquals(player, result.get(0).getPlayer());
        assertEquals(1, result.get(0).getValue());
        assertEquals(Instant.parse("2018-11-30T18:35:24.00Z"), result.get(0).getDate());
    }

    @Test
    void findScoreByPlayerID_2() throws Exception {
        restScoreMockMvc.perform(get("/api/score/{id}", Integer.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
