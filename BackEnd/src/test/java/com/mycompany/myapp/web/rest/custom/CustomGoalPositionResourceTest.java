package com.mycompany.myapp.web.rest.custom;

import com.mycompany.myapp.MyApp;
import com.mycompany.myapp.domain.GoalPosition;
import com.mycompany.myapp.domain.Map;
import com.mycompany.myapp.repository.GoalPositionRepository;
import com.mycompany.myapp.repository.MapRepository;
import com.mycompany.myapp.service.dto.CustomGoalPositionDTO;
import com.mycompany.myapp.web.rest.CustomGoalPositionResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MyApp.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomGoalPositionResourceTest {
    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private GoalPositionRepository goalPositionRepository;

    @Autowired
    private CustomGoalPositionResource customGoalPositionResource;

    @Autowired
    private MockMvc restGoalPositionMockMvc;

    @Test
    void findGoalPositionByMapId_1() {
        Map map = new Map();
        GoalPosition gp = new GoalPosition()
            .positionX(1)
            .positionY(1)
            .positionZ(1)
            .map(map);

        mapRepository.saveAndFlush(map);
        goalPositionRepository.saveAndFlush(gp);

        List<CustomGoalPositionDTO> result = customGoalPositionResource.findGoalPositionByMapId(map.getId());

        assertThat(result.get(0).getPositionX()).isEqualTo(gp.getPositionX());
        assertThat(result.get(0).getPositionY()).isEqualTo(gp.getPositionY());
        assertThat(result.get(0).getPositionZ()).isEqualTo(gp.getPositionZ());
    }

    @Test
    void findGoalPositionByMapId_2() throws Exception {
        restGoalPositionMockMvc.perform(get("/api/custom/goalPosition/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
