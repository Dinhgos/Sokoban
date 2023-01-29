package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.GoalPosition;
import com.mycompany.myapp.repository.GoalPositionRepository;

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
 * Integration tests for the {@link GoalPositionResource} REST controller.
 */
@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GoalPositionResourceIT {

    private static final Integer DEFAULT_POSITION_X = 1;
    private static final Integer UPDATED_POSITION_X = 2;

    private static final Integer DEFAULT_POSITION_Y = 1;
    private static final Integer UPDATED_POSITION_Y = 2;

    private static final Integer DEFAULT_POSITION_Z = 1;
    private static final Integer UPDATED_POSITION_Z = 2;

    @Autowired
    private GoalPositionRepository goalPositionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoalPositionMockMvc;

    private GoalPosition goalPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoalPosition createEntity(EntityManager em) {
        GoalPosition goalPosition = new GoalPosition()
            .positionX(DEFAULT_POSITION_X)
            .positionY(DEFAULT_POSITION_Y)
            .positionZ(DEFAULT_POSITION_Z);
        return goalPosition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoalPosition createUpdatedEntity(EntityManager em) {
        GoalPosition goalPosition = new GoalPosition()
            .positionX(UPDATED_POSITION_X)
            .positionY(UPDATED_POSITION_Y)
            .positionZ(UPDATED_POSITION_Z);
        return goalPosition;
    }

    @BeforeEach
    public void initTest() {
        goalPosition = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoalPosition() throws Exception {
        int databaseSizeBeforeCreate = goalPositionRepository.findAll().size();
        // Create the GoalPosition
        restGoalPositionMockMvc.perform(post("/api/goal-positions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goalPosition)))
            .andExpect(status().isCreated());

        // Validate the GoalPosition in the database
        List<GoalPosition> goalPositionList = goalPositionRepository.findAll();
        assertThat(goalPositionList).hasSize(databaseSizeBeforeCreate + 1);
        GoalPosition testGoalPosition = goalPositionList.get(goalPositionList.size() - 1);
        assertThat(testGoalPosition.getPositionX()).isEqualTo(DEFAULT_POSITION_X);
        assertThat(testGoalPosition.getPositionY()).isEqualTo(DEFAULT_POSITION_Y);
        assertThat(testGoalPosition.getPositionZ()).isEqualTo(DEFAULT_POSITION_Z);
    }

    @Test
    @Transactional
    public void createGoalPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goalPositionRepository.findAll().size();

        // Create the GoalPosition with an existing ID
        goalPosition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalPositionMockMvc.perform(post("/api/goal-positions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goalPosition)))
            .andExpect(status().isBadRequest());

        // Validate the GoalPosition in the database
        List<GoalPosition> goalPositionList = goalPositionRepository.findAll();
        assertThat(goalPositionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGoalPositions() throws Exception {
        // Initialize the database
        goalPositionRepository.saveAndFlush(goalPosition);

        // Get all the goalPositionList
        restGoalPositionMockMvc.perform(get("/api/goal-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goalPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionX").value(hasItem(DEFAULT_POSITION_X)))
            .andExpect(jsonPath("$.[*].positionY").value(hasItem(DEFAULT_POSITION_Y)))
            .andExpect(jsonPath("$.[*].positionZ").value(hasItem(DEFAULT_POSITION_Z)));
    }
    
    @Test
    @Transactional
    public void getGoalPosition() throws Exception {
        // Initialize the database
        goalPositionRepository.saveAndFlush(goalPosition);

        // Get the goalPosition
        restGoalPositionMockMvc.perform(get("/api/goal-positions/{id}", goalPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goalPosition.getId().intValue()))
            .andExpect(jsonPath("$.positionX").value(DEFAULT_POSITION_X))
            .andExpect(jsonPath("$.positionY").value(DEFAULT_POSITION_Y))
            .andExpect(jsonPath("$.positionZ").value(DEFAULT_POSITION_Z));
    }
    @Test
    @Transactional
    public void getNonExistingGoalPosition() throws Exception {
        // Get the goalPosition
        restGoalPositionMockMvc.perform(get("/api/goal-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoalPosition() throws Exception {
        // Initialize the database
        goalPositionRepository.saveAndFlush(goalPosition);

        int databaseSizeBeforeUpdate = goalPositionRepository.findAll().size();

        // Update the goalPosition
        GoalPosition updatedGoalPosition = goalPositionRepository.findById(goalPosition.getId()).get();
        // Disconnect from session so that the updates on updatedGoalPosition are not directly saved in db
        em.detach(updatedGoalPosition);
        updatedGoalPosition
            .positionX(UPDATED_POSITION_X)
            .positionY(UPDATED_POSITION_Y)
            .positionZ(UPDATED_POSITION_Z);

        restGoalPositionMockMvc.perform(put("/api/goal-positions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoalPosition)))
            .andExpect(status().isOk());

        // Validate the GoalPosition in the database
        List<GoalPosition> goalPositionList = goalPositionRepository.findAll();
        assertThat(goalPositionList).hasSize(databaseSizeBeforeUpdate);
        GoalPosition testGoalPosition = goalPositionList.get(goalPositionList.size() - 1);
        assertThat(testGoalPosition.getPositionX()).isEqualTo(UPDATED_POSITION_X);
        assertThat(testGoalPosition.getPositionY()).isEqualTo(UPDATED_POSITION_Y);
        assertThat(testGoalPosition.getPositionZ()).isEqualTo(UPDATED_POSITION_Z);
    }

    @Test
    @Transactional
    public void updateNonExistingGoalPosition() throws Exception {
        int databaseSizeBeforeUpdate = goalPositionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalPositionMockMvc.perform(put("/api/goal-positions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goalPosition)))
            .andExpect(status().isBadRequest());

        // Validate the GoalPosition in the database
        List<GoalPosition> goalPositionList = goalPositionRepository.findAll();
        assertThat(goalPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoalPosition() throws Exception {
        // Initialize the database
        goalPositionRepository.saveAndFlush(goalPosition);

        int databaseSizeBeforeDelete = goalPositionRepository.findAll().size();

        // Delete the goalPosition
        restGoalPositionMockMvc.perform(delete("/api/goal-positions/{id}", goalPosition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoalPosition> goalPositionList = goalPositionRepository.findAll();
        assertThat(goalPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
