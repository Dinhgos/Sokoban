package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Walls;
import com.mycompany.myapp.repository.WallsRepository;

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
 * Integration tests for the {@link WallsResource} REST controller.
 */
@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WallsResourceIT {

    private static final Integer DEFAULT_POSITION_X = 1;
    private static final Integer UPDATED_POSITION_X = 2;

    private static final Integer DEFAULT_POSITION_Y = 1;
    private static final Integer UPDATED_POSITION_Y = 2;

    private static final Integer DEFAULT_POSITION_Z = 1;
    private static final Integer UPDATED_POSITION_Z = 2;

    @Autowired
    private WallsRepository wallsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWallsMockMvc;

    private Walls walls;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Walls createEntity(EntityManager em) {
        Walls walls = new Walls()
            .positionX(DEFAULT_POSITION_X)
            .positionY(DEFAULT_POSITION_Y)
            .positionZ(DEFAULT_POSITION_Z);
        return walls;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Walls createUpdatedEntity(EntityManager em) {
        Walls walls = new Walls()
            .positionX(UPDATED_POSITION_X)
            .positionY(UPDATED_POSITION_Y)
            .positionZ(UPDATED_POSITION_Z);
        return walls;
    }

    @BeforeEach
    public void initTest() {
        walls = createEntity(em);
    }

    @Test
    @Transactional
    public void createWalls() throws Exception {
        int databaseSizeBeforeCreate = wallsRepository.findAll().size();
        // Create the Walls
        restWallsMockMvc.perform(post("/api/walls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(walls)))
            .andExpect(status().isCreated());

        // Validate the Walls in the database
        List<Walls> wallsList = wallsRepository.findAll();
        assertThat(wallsList).hasSize(databaseSizeBeforeCreate + 1);
        Walls testWalls = wallsList.get(wallsList.size() - 1);
        assertThat(testWalls.getPositionX()).isEqualTo(DEFAULT_POSITION_X);
        assertThat(testWalls.getPositionY()).isEqualTo(DEFAULT_POSITION_Y);
        assertThat(testWalls.getPositionZ()).isEqualTo(DEFAULT_POSITION_Z);
    }

    @Test
    @Transactional
    public void createWallsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wallsRepository.findAll().size();

        // Create the Walls with an existing ID
        walls.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWallsMockMvc.perform(post("/api/walls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(walls)))
            .andExpect(status().isBadRequest());

        // Validate the Walls in the database
        List<Walls> wallsList = wallsRepository.findAll();
        assertThat(wallsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWalls() throws Exception {
        // Initialize the database
        wallsRepository.saveAndFlush(walls);

        // Get all the wallsList
        restWallsMockMvc.perform(get("/api/walls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(walls.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionX").value(hasItem(DEFAULT_POSITION_X)))
            .andExpect(jsonPath("$.[*].positionY").value(hasItem(DEFAULT_POSITION_Y)))
            .andExpect(jsonPath("$.[*].positionZ").value(hasItem(DEFAULT_POSITION_Z)));
    }
    
    @Test
    @Transactional
    public void getWalls() throws Exception {
        // Initialize the database
        wallsRepository.saveAndFlush(walls);

        // Get the walls
        restWallsMockMvc.perform(get("/api/walls/{id}", walls.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(walls.getId().intValue()))
            .andExpect(jsonPath("$.positionX").value(DEFAULT_POSITION_X))
            .andExpect(jsonPath("$.positionY").value(DEFAULT_POSITION_Y))
            .andExpect(jsonPath("$.positionZ").value(DEFAULT_POSITION_Z));
    }
    @Test
    @Transactional
    public void getNonExistingWalls() throws Exception {
        // Get the walls
        restWallsMockMvc.perform(get("/api/walls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWalls() throws Exception {
        // Initialize the database
        wallsRepository.saveAndFlush(walls);

        int databaseSizeBeforeUpdate = wallsRepository.findAll().size();

        // Update the walls
        Walls updatedWalls = wallsRepository.findById(walls.getId()).get();
        // Disconnect from session so that the updates on updatedWalls are not directly saved in db
        em.detach(updatedWalls);
        updatedWalls
            .positionX(UPDATED_POSITION_X)
            .positionY(UPDATED_POSITION_Y)
            .positionZ(UPDATED_POSITION_Z);

        restWallsMockMvc.perform(put("/api/walls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWalls)))
            .andExpect(status().isOk());

        // Validate the Walls in the database
        List<Walls> wallsList = wallsRepository.findAll();
        assertThat(wallsList).hasSize(databaseSizeBeforeUpdate);
        Walls testWalls = wallsList.get(wallsList.size() - 1);
        assertThat(testWalls.getPositionX()).isEqualTo(UPDATED_POSITION_X);
        assertThat(testWalls.getPositionY()).isEqualTo(UPDATED_POSITION_Y);
        assertThat(testWalls.getPositionZ()).isEqualTo(UPDATED_POSITION_Z);
    }

    @Test
    @Transactional
    public void updateNonExistingWalls() throws Exception {
        int databaseSizeBeforeUpdate = wallsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWallsMockMvc.perform(put("/api/walls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(walls)))
            .andExpect(status().isBadRequest());

        // Validate the Walls in the database
        List<Walls> wallsList = wallsRepository.findAll();
        assertThat(wallsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWalls() throws Exception {
        // Initialize the database
        wallsRepository.saveAndFlush(walls);

        int databaseSizeBeforeDelete = wallsRepository.findAll().size();

        // Delete the walls
        restWallsMockMvc.perform(delete("/api/walls/{id}", walls.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Walls> wallsList = wallsRepository.findAll();
        assertThat(wallsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
