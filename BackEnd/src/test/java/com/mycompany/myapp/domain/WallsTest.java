package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class WallsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Walls.class);
        Walls walls1 = new Walls();
        walls1.setId(1L);
        Walls walls2 = new Walls();
        walls2.setId(walls1.getId());
        assertThat(walls1).isEqualTo(walls2);
        walls2.setId(2L);
        assertThat(walls1).isNotEqualTo(walls2);
        walls1.setId(null);
        assertThat(walls1).isNotEqualTo(walls2);
    }
}
