package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Boxes;
import com.mycompany.myapp.repository.BoxesRepository;

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
 * Integration tests for the {@link BoxesResource} REST controller.
 */
@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BoxesResourceIT {

    private static final Integer DEFAULT_POSITION_X = 1;
    private static final Integer UPDATED_POSITION_X = 2;

    private static final Integer DEFAULT_POSITION_Y = 1;
    private static final Integer UPDATED_POSITION_Y = 2;

    private static final Integer DEFAULT_POSITION_Z = 1;
    private static final Integer UPDATED_POSITION_Z = 2;

    @Autowired
    private BoxesRepository boxesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoxesMockMvc;

    private Boxes boxes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boxes createEntity(EntityManager em) {
        Boxes boxes = new Boxes()
            .positionX(DEFAULT_POSITION_X)
            .positionY(DEFAULT_POSITION_Y)
            .positionZ(DEFAULT_POSITION_Z);
        return boxes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Boxes createUpdatedEntity(EntityManager em) {
        Boxes boxes = new Boxes()
            .positionX(UPDATED_POSITION_X)
            .positionY(UPDATED_POSITION_Y)
            .positionZ(UPDATED_POSITION_Z);
        return boxes;
    }

    @BeforeEach
    public void initTest() {
        boxes = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoxes() throws Exception {
        int databaseSizeBeforeCreate = boxesRepository.findAll().size();
        // Create the Boxes
        restBoxesMockMvc.perform(post("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxes)))
            .andExpect(status().isCreated());

        // Validate the Boxes in the database
        List<Boxes> boxesList = boxesRepository.findAll();
        assertThat(boxesList).hasSize(databaseSizeBeforeCreate + 1);
        Boxes testBoxes = boxesList.get(boxesList.size() - 1);
        assertThat(testBoxes.getPositionX()).isEqualTo(DEFAULT_POSITION_X);
        assertThat(testBoxes.getPositionY()).isEqualTo(DEFAULT_POSITION_Y);
        assertThat(testBoxes.getPositionZ()).isEqualTo(DEFAULT_POSITION_Z);
    }

    @Test
    @Transactional
    public void createBoxesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boxesRepository.findAll().size();

        // Create the Boxes with an existing ID
        boxes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoxesMockMvc.perform(post("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxes)))
            .andExpect(status().isBadRequest());

        // Validate the Boxes in the database
        List<Boxes> boxesList = boxesRepository.findAll();
        assertThat(boxesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBoxes() throws Exception {
        // Initialize the database
        boxesRepository.saveAndFlush(boxes);

        // Get all the boxesList
        restBoxesMockMvc.perform(get("/api/boxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boxes.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionX").value(hasItem(DEFAULT_POSITION_X)))
            .andExpect(jsonPath("$.[*].positionY").value(hasItem(DEFAULT_POSITION_Y)))
            .andExpect(jsonPath("$.[*].positionZ").value(hasItem(DEFAULT_POSITION_Z)));
    }

    @Test
    @Transactional
    public void getBoxes() throws Exception {
        // Initialize the database
        boxesRepository.saveAndFlush(boxes);

        // Get the boxes
        restBoxesMockMvc.perform(get("/api/boxes/{id}", boxes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boxes.getId().intValue()))
            .andExpect(jsonPath("$.positionX").value(DEFAULT_POSITION_X))
            .andExpect(jsonPath("$.positionY").value(DEFAULT_POSITION_Y))
            .andExpect(jsonPath("$.positionZ").value(DEFAULT_POSITION_Z));
    }
    @Test
    @Transactional
    public void getNonExistingBoxes() throws Exception {
        // Get the boxes
        restBoxesMockMvc.perform(get("/api/boxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoxes() throws Exception {
        // Initialize the database
        boxesRepository.saveAndFlush(boxes);

        int databaseSizeBeforeUpdate = boxesRepository.findAll().size();

        // Update the boxes
        Boxes updatedBoxes = boxesRepository.findById(boxes.getId()).get();
        // Disconnect from session so that the updates on updatedBoxes are not directly saved in db
        em.detach(updatedBoxes);
        updatedBoxes
            .positionX(UPDATED_POSITION_X)
            .positionY(UPDATED_POSITION_Y)
            .positionZ(UPDATED_POSITION_Z);

        restBoxesMockMvc.perform(put("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoxes)))
            .andExpect(status().isOk());

        // Validate the Boxes in the database
        List<Boxes> boxesList = boxesRepository.findAll();
        assertThat(boxesList).hasSize(databaseSizeBeforeUpdate);
        Boxes testBoxes = boxesList.get(boxesList.size() - 1);
        assertThat(testBoxes.getPositionX()).isEqualTo(UPDATED_POSITION_X);
        assertThat(testBoxes.getPositionY()).isEqualTo(UPDATED_POSITION_Y);
        assertThat(testBoxes.getPositionZ()).isEqualTo(UPDATED_POSITION_Z);
    }

    @Test
    @Transactional
    public void updateNonExistingBoxes() throws Exception {
        int databaseSizeBeforeUpdate = boxesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoxesMockMvc.perform(put("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxes)))
            .andExpect(status().isBadRequest());

        // Validate the Boxes in the database
        List<Boxes> boxesList = boxesRepository.findAll();
        assertThat(boxesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBoxes() throws Exception {
        // Initialize the database
        boxesRepository.saveAndFlush(boxes);

        int databaseSizeBeforeDelete = boxesRepository.findAll().size();

        // Delete the boxes
        restBoxesMockMvc.perform(delete("/api/boxes/{id}", boxes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Boxes> boxesList = boxesRepository.findAll();
        assertThat(boxesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
