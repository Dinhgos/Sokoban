package com.mycompany.myapp.web.rest.custom;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Player;
import com.mycompany.myapp.repository.PlayerRepository;
import com.mycompany.myapp.web.rest.PlayerResource;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomPlayerResourceTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerResource playerResource;

    private Player player;

    @BeforeEach
    public void initTest() {
        player = new Player()
            .name("testName")
            .password("testPas")
            .level(1);
    }

    @Test
    void findPlayerByName_1() {
        playerRepository.saveAndFlush(player);
        ResponseEntity<Player> result = playerResource.findPlayerByName(player.getName());

        assertEquals("testName", result.getBody().getName());
        assertEquals("testPas", result.getBody().getPassword());
        assertEquals(1, result.getBody().getLevel());
    }

    @Test
    void findPlayerByName_2() throws Exception {
        mockMvc.perform(get("/api/player/{name}", "Long.MAX_VALUE"))
            .andExpect(status().isNotFound());
    }

    @Test
    void updatePlayer_1() throws URISyntaxException {
        playerRepository.saveAndFlush(player);
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        ResponseEntity<Player> result = playerResource.updatePlayer(player.getId(), 2);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(player.getId().intValue(),result.getBody().getId());
        assertEquals("testName", result.getBody().getName());
        assertEquals("testPas", result.getBody().getPassword());
        assertEquals(2, result.getBody().getLevel());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getName()).isEqualTo("testName");
        assertThat(testPlayer.getPassword()).isEqualTo("testPas");
        assertThat(testPlayer.getLevel()).isEqualTo(2);
    }

    @Test
    void updatePlayer_2() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        mockMvc.perform(put("/api/custom/player")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }
}
