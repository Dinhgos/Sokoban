package com.mycompany.myapp.web.rest.custom;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.service.dto.CustomBoxesDTO;
import com.mycompany.myapp.service.dto.CustomGoalPositionDTO;
import com.mycompany.myapp.service.dto.CustomMapDTO;
import com.mycompany.myapp.service.dto.CustomWallsDTO;
import com.mycompany.myapp.web.rest.MapResource;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomMapResourceTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MapResource mapResource;

    private Map map;

    @BeforeEach
    public void initTest() {
        map = new Map()
            .playerPositionX(1)
            .playerPositionY(1)
            .playerPositionZ(1);
    }

    @Test
    void findMapById_1() {
        mapRepository.saveAndFlush(map);

        ResponseEntity<CustomMapDTO> result = mapResource.findMapById(map.getId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(map.getId().intValue(), result.getBody().getMapId());
        assertEquals(1, result.getBody().getPlayerPositionX());
        assertEquals(1, result.getBody().getPlayerPositionY());
        assertEquals(1, result.getBody().getPlayerPositionZ());
    }

    @Test
    void findMapById_2() throws Exception {
        mockMvc.perform(get("/api/custom/map/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    void createCustomMap() throws Exception {
        int databaseSizeBeforeCreate = mapRepository.findAll().size();

        CustomMapDTO mapDTO = new CustomMapDTO();
        mapDTO.setPlayerPositionX(2);
        mapDTO.setPlayerPositionY(2);
        mapDTO.setPlayerPositionZ(2);

        List<CustomBoxesDTO> list1 = new ArrayList<>();
        list1.add(new CustomBoxesDTO());
        mapDTO.setBoxes(list1);

        List<CustomGoalPositionDTO> list2 = new ArrayList<>();
        list2.add(new CustomGoalPositionDTO());
        mapDTO.setGoalPositions(list2);

        List<CustomWallsDTO> list3 = new ArrayList<>();
        list3.add(new CustomWallsDTO());
        mapDTO.setWalls(list3);

        mockMvc.perform(post("/api/custom/map")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mapDTO)))
            .andExpect(status().isOk());

        List<Map> mapList = mapRepository.findAll();
        assertThat(mapList).hasSize(databaseSizeBeforeCreate + 1);
        Map testMap = mapList.get(mapList.size() - 1);
        assertThat(testMap.getPlayerPositionX()).isEqualTo(2);
        assertThat(testMap.getPlayerPositionY()).isEqualTo(2);
        assertThat(testMap.getPlayerPositionZ()).isEqualTo(2);
    }
}
