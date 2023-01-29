package com.mycompany.myapp.web.rest.custom;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Boxes;
import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.domain.Player;
import com.mycompany.myapp.domain.Save;
import com.mycompany.myapp.repository.BoxesRepository;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.repository.PlayerRepository;
import com.mycompany.myapp.repository.SaveRepository;
import com.mycompany.myapp.service.dto.CustomBoxesDTO;
import com.mycompany.myapp.service.dto.CustomSaveDTO;
import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomSaveResourceTest {
    @Autowired
    private SaveRepository saveRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private BoxesRepository boxesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaveMockMvc;

    private Save save;

    @BeforeEach
    public void initTest() {
        save = new Save()
            .moves(1)
            .time(1)
            .playerPositionX(1)
            .playerPositionY(1)
            .playerPositionZ(1);
    }

    @Test
    void findSaveByPLayerId_1() throws Exception {
        Player player = new Player();
        save.setPlayer(player);

        // Initialize the database
        playerRepository.saveAndFlush(player);
        saveRepository.saveAndFlush(save);

        // Get all the saveList
        restSaveMockMvc.perform(get("/api/custom/save/byPlayerId/{id}", player.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(save.getId().intValue())))
            .andExpect(jsonPath("$.[*].moves").value(hasItem(1)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(1)))
            .andExpect(jsonPath("$.[*].playerPositionX").value(hasItem(1)))
            .andExpect(jsonPath("$.[*].playerPositionY").value(hasItem(1)))
            .andExpect(jsonPath("$.[*].playerPositionZ").value(hasItem(1)));
    }

    @Test
    void findSaveByPLayerId_2() throws Exception {
        restSaveMockMvc.perform(get("/api/custom/save/byPlayerId/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    void getMapsSaves_1() throws Exception {
        Map map = new Map();
        save.setMap(map);

        mapRepository.saveAndFlush(map);
        saveRepository.saveAndFlush(save);

        // Get all the saveList
        restSaveMockMvc.perform(get("/api/custom/save/byMapId/{id}", map.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(save.getId().intValue())))
            .andExpect(jsonPath("$.[*].moves").value(hasItem(1)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(1)))
            .andExpect(jsonPath("$.[*].playerPositionX").value(hasItem(1)))
            .andExpect(jsonPath("$.[*].playerPositionY").value(hasItem(1)))
            .andExpect(jsonPath("$.[*].playerPositionZ").value(hasItem(1)));
    }

    @Test
    void getMapsSaves_2() throws Exception {
        restSaveMockMvc.perform(get("/api/custom/save/byMapId/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    void createSave() throws Exception {
        int databaseSizeBeforeCreate = saveRepository.findAll().size();

        CustomSaveDTO saveDTO = new CustomSaveDTO();
        saveDTO.setMoves(1);
        saveDTO.setTime(1);
        saveDTO.setPlayerPositionX(1);
        saveDTO.setPlayerPositionY(1);
        saveDTO.setPlayerPositionZ(1);

        List<CustomBoxesDTO> list1 = new ArrayList<>();
        list1.add(new CustomBoxesDTO());
        saveDTO.setBoxes(list1);

        Map map = new Map();
        Player player = new Player();

        mapRepository.saveAndFlush(map);
        playerRepository.saveAndFlush(player);

        saveDTO.setFkMapId(map.getId());
        saveDTO.setFkPlayerId(player.getId());

        // Create the Save
        restSaveMockMvc.perform(post("/api/custom/createSave")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saveDTO)))
            .andExpect(status().isCreated());

        // Validate the Save in the database
        List<Save> saveList = saveRepository.findAll();
        assertThat(saveList).hasSize(databaseSizeBeforeCreate + 1);
        Save testSave = saveList.get(saveList.size() - 1);
        assertThat(testSave.getMoves()).isEqualTo(1);
        assertThat(testSave.getTime()).isEqualTo(1);
        assertThat(testSave.getPlayerPositionX()).isEqualTo(1);
        assertThat(testSave.getPlayerPositionY()).isEqualTo(1);
        assertThat(testSave.getPlayerPositionZ()).isEqualTo(1);
    }

    @Test
    void updateSave() throws Exception {
        // Initialize the database
        saveRepository.saveAndFlush(save);

        Boxes boxes = new Boxes();
        boxes.setSave(save);
        boxesRepository.saveAndFlush(boxes);
        save.setBoxes(Collections.singleton(boxes));

        Map map = new Map();
        mapRepository.saveAndFlush(map);

        Player player = new Player();
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = saveRepository.findAll().size();

        // Update the save
        CustomSaveDTO saveDTO = new CustomSaveDTO();
        saveDTO.setId(save.getId());
        saveDTO.setMoves(2);
        saveDTO.setTime(2);
        saveDTO.setPlayerPositionX(2);
        saveDTO.setPlayerPositionY(2);
        saveDTO.setPlayerPositionZ(2);

        saveDTO.setFkMapId(map.getId());
        saveDTO.setFkPlayerId(player.getId());

        List<CustomBoxesDTO> list1 = new ArrayList<>();
        list1.add(new CustomBoxesDTO());
        saveDTO.setBoxes(list1);

        restSaveMockMvc.perform(put("/api/custom/updateSave")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saveDTO)))
            .andExpect(status().isOk());

        // Validate the Save in the database
        List<Save> saveList = saveRepository.findAll();
        assertThat(saveList).hasSize(databaseSizeBeforeUpdate);
        Save testSave = saveList.get(saveList.size() - 1);
        assertThat(testSave.getMoves()).isEqualTo(2);
        assertThat(testSave.getTime()).isEqualTo(2);
        assertThat(testSave.getPlayerPositionX()).isEqualTo(2);
        assertThat(testSave.getPlayerPositionY()).isEqualTo(2);
        assertThat(testSave.getPlayerPositionZ()).isEqualTo(2);
    }


    @Test
    void deleteSaveByPlayerId() throws Exception {
        Player player = new Player();
        save.setPlayer(player);

        // Initialize the database
        playerRepository.saveAndFlush(player);
        saveRepository.saveAndFlush(save);

        int databaseSizeBeforeDelete = saveRepository.findAll().size();

        // Delete the save
        restSaveMockMvc.perform(delete("/api/custom/deleteSave/{id}", player.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Save> saveList = saveRepository.findAll();
        assertThat(saveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
