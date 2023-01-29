package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class GoalPositionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalPosition.class);
        GoalPosition goalPosition1 = new GoalPosition();
        goalPosition1.setId(1L);
        GoalPosition goalPosition2 = new GoalPosition();
        goalPosition2.setId(goalPosition1.getId());
        assertThat(goalPosition1).isEqualTo(goalPosition2);
        goalPosition2.setId(2L);
        assertThat(goalPosition1).isNotEqualTo(goalPosition2);
        goalPosition1.setId(null);
        assertThat(goalPosition1).isNotEqualTo(goalPosition2);
    }
}
