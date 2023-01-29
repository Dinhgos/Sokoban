package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Save;
import com.mycompany.myapp.repository.SaveRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SaveResource} REST controller.
 */
@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SaveResourceIT {

    private static final Integer DEFAULT_MOVES = 1;
    private static final Integer UPDATED_MOVES = 2;

    private static final Integer DEFAULT_TIME = 1;
    private static final Integer UPDATED_TIME = 2;

    private static final Integer DEFAULT_PLAYER_POSITION_X = 1;
    private static final Integer UPDATED_PLAYER_POSITION_X = 2;

    private static final Integer DEFAULT_PLAYER_POSITION_Y = 1;
    private static final Integer UPDATED_PLAYER_POSITION_Y = 2;

    private static final Integer DEFAULT_PLAYER_POSITION_Z = 1;
    private static final Integer UPDATED_PLAYER_POSITION_Z = 2;

    @Autowired
    private SaveRepository saveRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaveMockMvc;

    private Save save;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Save createEntity(EntityManager em) {
        Save save = new Save()
            .moves(DEFAULT_MOVES)
            .time(DEFAULT_TIME)
            .playerPositionX(DEFAULT_PLAYER_POSITION_X)
            .playerPositionY(DEFAULT_PLAYER_POSITION_Y)
            .playerPositionZ(DEFAULT_PLAYER_POSITION_Z);
        return save;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Save createUpdatedEntity(EntityManager em) {
        Save save = new Save()
            .moves(UPDATED_MOVES)
            .time(UPDATED_TIME)
            .playerPositionX(UPDATED_PLAYER_POSITION_X)
            .playerPositionY(UPDATED_PLAYER_POSITION_Y)
            .playerPositionZ(UPDATED_PLAYER_POSITION_Z);
        return save;
    }

    @BeforeEach
    public void initTest() {
        save = createEntity(em);
    }

    @Test
    @Transactional
    public void createSave() throws Exception {
        int databaseSizeBeforeCreate = saveRepository.findAll().size();
        // Create the Save
        restSaveMockMvc.perform(post("/api/saves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(save)))
            .andExpect(status().isCreated());

        // Validate the Save in the database
        List<Save> saveList = saveRepository.findAll();
        assertThat(saveList).hasSize(databaseSizeBeforeCreate + 1);
        Save testSave = saveList.get(saveList.size() - 1);
        assertThat(testSave.getMoves()).isEqualTo(DEFAULT_MOVES);
        assertThat(testSave.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testSave.getPlayerPositionX()).isEqualTo(DEFAULT_PLAYER_POSITION_X);
        assertThat(testSave.getPlayerPositionY()).isEqualTo(DEFAULT_PLAYER_POSITION_Y);
        assertThat(testSave.getPlayerPositionZ()).isEqualTo(DEFAULT_PLAYER_POSITION_Z);
    }

    @Test
    @Transactional
    public void createSaveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saveRepository.findAll().size();

        // Create the Save with an existing ID
        save.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaveMockMvc.perform(post("/api/saves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(save)))
            .andExpect(status().isBadRequest());

        // Validate the Save in the database
        List<Save> saveList = saveRepository.findAll();
        assertThat(saveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSaves() throws Exception {
        // Initialize the database
        saveRepository.saveAndFlush(save);

        // Get all the saveList
        restSaveMockMvc.perform(get("/api/saves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(save.getId().intValue())))
            .andExpect(jsonPath("$.[*].moves").value(hasItem(DEFAULT_MOVES)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].playerPositionX").value(hasItem(DEFAULT_PLAYER_POSITION_X)))
            .andExpect(jsonPath("$.[*].playerPositionY").value(hasItem(DEFAULT_PLAYER_POSITION_Y)))
            .andExpect(jsonPath("$.[*].playerPositionZ").value(hasItem(DEFAULT_PLAYER_POSITION_Z)));
    }
    
    @Test
    @Transactional
    public void getSave() throws Exception {
        // Initialize the database
        saveRepository.saveAndFlush(save);

        // Get the save
        restSaveMockMvc.perform(get("/api/saves/{id}", save.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(save.getId().intValue()))
            .andExpect(jsonPath("$.moves").value(DEFAULT_MOVES))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.playerPositionX").value(DEFAULT_PLAYER_POSITION_X))
            .andExpect(jsonPath("$.playerPositionY").value(DEFAULT_PLAYER_POSITION_Y))
            .andExpect(jsonPath("$.playerPositionZ").value(DEFAULT_PLAYER_POSITION_Z));
    }
    @Test
    @Transactional
    public void getNonExistingSave() throws Exception {
        // Get the save
        restSaveMockMvc.perform(get("/api/saves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSave() throws Exception {
        // Initialize the database
        saveRepository.saveAndFlush(save);

        int databaseSizeBeforeUpdate = saveRepository.findAll().size();

        // Update the save
        Save updatedSave = saveRepository.findById(save.getId()).get();
        // Disconnect from session so that the updates on updatedSave are not directly saved in db
        em.detach(updatedSave);
        updatedSave
            .moves(UPDATED_MOVES)
            .time(UPDATED_TIME)
            .playerPositionX(UPDATED_PLAYER_POSITION_X)
            .playerPositionY(UPDATED_PLAYER_POSITION_Y)
            .playerPositionZ(UPDATED_PLAYER_POSITION_Z);

        restSaveMockMvc.perform(put("/api/saves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSave)))
            .andExpect(status().isOk());

        // Validate the Save in the database
        List<Save> saveList = saveRepository.findAll();
        assertThat(saveList).hasSize(databaseSizeBeforeUpdate);
        Save testSave = saveList.get(saveList.size() - 1);
        assertThat(testSave.getMoves()).isEqualTo(UPDATED_MOVES);
        assertThat(testSave.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testSave.getPlayerPositionX()).isEqualTo(UPDATED_PLAYER_POSITION_X);
        assertThat(testSave.getPlayerPositionY()).isEqualTo(UPDATED_PLAYER_POSITION_Y);
        assertThat(testSave.getPlayerPositionZ()).isEqualTo(UPDATED_PLAYER_POSITION_Z);
    }

    @Test
    @Transactional
    public void updateNonExistingSave() throws Exception {
        int databaseSizeBeforeUpdate = saveRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaveMockMvc.perform(put("/api/saves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(save)))
            .andExpect(status().isBadRequest());

        // Validate the Save in the database
        List<Save> saveList = saveRepository.findAll();
        assertThat(saveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSave() throws Exception {
        // Initialize the database
        saveRepository.saveAndFlush(save);

        int databaseSizeBeforeDelete = saveRepository.findAll().size();

        // Delete the save
        restSaveMockMvc.perform(delete("/api/saves/{id}", save.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Save> saveList = saveRepository.findAll();
        assertThat(saveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
