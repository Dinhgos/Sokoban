package com.mycompany.myapp.web.rest.custom;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.Boxes;
import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.domain.Save;
import com.mycompany.myapp.repository.BoxesRepository;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.repository.SaveRepository;
import com.mycompany.myapp.service.dto.CustomBoxesDTO;
import com.mycompany.myapp.web.rest.BoxesResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomBoxesResourceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoxesMockMvc;

    @Autowired
    private BoxesRepository boxesRepository;

    @Autowired
    private SaveRepository saveRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private BoxesResource boxesResource;

    @Test
    void findBoxesByMapId_1() throws Exception {
        Map map = new Map();
        Boxes box = new Boxes()
            .positionX(1)
            .positionY(1)
            .positionZ(1)
            .map(map);

        mapRepository.saveAndFlush(map);
        boxesRepository.saveAndFlush(box);

        List<CustomBoxesDTO> result = boxesResource.findBoxesByMapId(map.getId());

        assertThat(result.get(0).getPositionX()).isEqualTo(box.getPositionX());
        assertThat(result.get(0).getPositionY()).isEqualTo(box.getPositionY());
        assertThat(result.get(0).getPositionZ()).isEqualTo(box.getPositionZ());
    }

    @Test
    void findBoxesByMapId_2() throws Exception {
        restBoxesMockMvc.perform(get("/api/custom/boxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    void findBoxesBySaveId_1() {
        Save save = new Save();
        Boxes box = new Boxes()
            .positionX(1)
            .positionY(1)
            .positionZ(1)
            .save(save);

        saveRepository.saveAndFlush(save);
        boxesRepository.saveAndFlush(box);

        List<Boxes> result = boxesResource.findBoxesBySaveId(save.getId());

        assertThat(result.get(0).getPositionX()).isEqualTo(box.getPositionX());
        assertThat(result.get(0).getPositionY()).isEqualTo(box.getPositionY());
        assertThat(result.get(0).getPositionZ()).isEqualTo(box.getPositionZ());
        assertThat(result.get(0).getSave()).isEqualTo(save);
    }

    @Test
    void findBoxesBySaveId_2() throws Exception {
        restBoxesMockMvc.perform(get("/api/custom/boxes/bySaveId/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteBoxesBySaveId() throws Exception {
        // Initialize the database
        Save save = new Save();
        Boxes box = new Boxes()
            .positionX(1)
            .positionY(1)
            .positionZ(1)
            .save(save);

        saveRepository.saveAndFlush(save);
        boxesRepository.saveAndFlush(box);

        int databaseSizeBeforeDelete = boxesRepository.findAll().size();

        // Delete the boxes
        restBoxesMockMvc.perform(delete("/api/custom/deleteBoxes/{id}", save.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Boxes> boxesList = boxesRepository.findAll();
        assertThat(boxesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
