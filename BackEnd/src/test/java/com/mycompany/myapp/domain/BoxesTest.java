package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BoxesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boxes.class);
        Boxes boxes1 = new Boxes();
        boxes1.setId(1L);
        Boxes boxes2 = new Boxes();
        boxes2.setId(boxes1.getId());
        assertThat(boxes1).isEqualTo(boxes2);
        boxes2.setId(2L);
        assertThat(boxes1).isNotEqualTo(boxes2);
        boxes1.setId(null);
        assertThat(boxes1).isNotEqualTo(boxes2);
    }
}
