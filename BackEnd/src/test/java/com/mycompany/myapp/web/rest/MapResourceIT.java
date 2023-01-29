package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.repository.MapRepository;

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
 * Integration tests for the {@link MapResource} REST controller.
 */
@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MapResourceIT {

    private static final Integer DEFAULT_PLAYER_POSITION_X = 1;
    private static final Integer UPDATED_PLAYER_POSITION_X = 2;

    private static final Integer DEFAULT_PLAYER_POSITION_Y = 1;
    private static final Integer UPDATED_PLAYER_POSITION_Y = 2;

    private static final Integer DEFAULT_PLAYER_POSITION_Z = 1;
    private static final Integer UPDATED_PLAYER_POSITION_Z = 2;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMapMockMvc;

    private Map map;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Map createEntity(EntityManager em) {
        Map map = new Map()
            .playerPositionX(DEFAULT_PLAYER_POSITION_X)
            .playerPositionY(DEFAULT_PLAYER_POSITION_Y)
            .playerPositionZ(DEFAULT_PLAYER_POSITION_Z);
        return map;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Map createUpdatedEntity(EntityManager em) {
        Map map = new Map()
            .playerPositionX(UPDATED_PLAYER_POSITION_X)
            .playerPositionY(UPDATED_PLAYER_POSITION_Y)
            .playerPositionZ(UPDATED_PLAYER_POSITION_Z);
        return map;
    }

    @BeforeEach
    public void initTest() {
        map = createEntity(em);
    }

    @Test
    @Transactional
    public void createMap() throws Exception {
        int databaseSizeBeforeCreate = mapRepository.findAll().size();
        // Create the Map
        restMapMockMvc.perform(post("/api/maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(map)))
            .andExpect(status().isCreated());

        // Validate the Map in the database
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeCreate + 1);
        Map testMap = mapList.get(mapList.size() - 1);
        assertThat(testMap.getPlayerPositionX()).isEqualTo(DEFAULT_PLAYER_POSITION_X);
        assertThat(testMap.getPlayerPositionY()).isEqualTo(DEFAULT_PLAYER_POSITION_Y);
        assertThat(testMap.getPlayerPositionZ()).isEqualTo(DEFAULT_PLAYER_POSITION_Z);
    }

    @Test
    @Transactional
    public void createMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mapRepository.findAll().size();

        // Create the Map with an existing ID
        map.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMapMockMvc.perform(post("/api/maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(map)))
            .andExpect(status().isBadRequest());

        // Validate the Map in the database
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMaps() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get all the mapList
        restMapMockMvc.perform(get("/api/maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(map.getId().intValue())))
            .andExpect(jsonPath("$.[*].playerPositionX").value(hasItem(DEFAULT_PLAYER_POSITION_X)))
            .andExpect(jsonPath("$.[*].playerPositionY").value(hasItem(DEFAULT_PLAYER_POSITION_Y)))
            .andExpect(jsonPath("$.[*].playerPositionZ").value(hasItem(DEFAULT_PLAYER_POSITION_Z)));
    }
    
    @Test
    @Transactional
    public void getMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}", map.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(map.getId().intValue()))
            .andExpect(jsonPath("$.playerPositionX").value(DEFAULT_PLAYER_POSITION_X))
            .andExpect(jsonPath("$.playerPositionY").value(DEFAULT_PLAYER_POSITION_Y))
            .andExpect(jsonPath("$.playerPositionZ").value(DEFAULT_PLAYER_POSITION_Z));
    }
    @Test
    @Transactional
    public void getNonExistingMap() throws Exception {
        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        int databaseSizeBeforeUpdate = mapRepository.findAll().size();

        // Update the map
        Map updatedMap = mapRepository.findById(map.getId()).get();
        // Disconnect from session so that the updates on updatedMap are not directly saved in db
        em.detach(updatedMap);
        updatedMap
            .playerPositionX(UPDATED_PLAYER_POSITION_X)
            .playerPositionY(UPDATED_PLAYER_POSITION_Y)
            .playerPositionZ(UPDATED_PLAYER_POSITION_Z);

        restMapMockMvc.perform(put("/api/maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMap)))
            .andExpect(status().isOk());

        // Validate the Map in the database
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeUpdate);
        Map testMap = mapList.get(mapList.size() - 1);
        assertThat(testMap.getPlayerPositionX()).isEqualTo(UPDATED_PLAYER_POSITION_X);
        assertThat(testMap.getPlayerPositionY()).isEqualTo(UPDATED_PLAYER_POSITION_Y);
        assertThat(testMap.getPlayerPositionZ()).isEqualTo(UPDATED_PLAYER_POSITION_Z);
    }

    @Test
    @Transactional
    public void updateNonExistingMap() throws Exception {
        int databaseSizeBeforeUpdate = mapRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMapMockMvc.perform(put("/api/maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(map)))
            .andExpect(status().isBadRequest());

        // Validate the Map in the database
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        int databaseSizeBeforeDelete = mapRepository.findAll().size();

        // Delete the map
        restMapMockMvc.perform(delete("/api/maps/{id}", map.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
