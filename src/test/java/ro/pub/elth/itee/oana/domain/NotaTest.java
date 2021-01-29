package ro.pub.elth.itee.oana.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ro.pub.elth.itee.oana.web.rest.TestUtil;

public class NotaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nota.class);
        Nota nota1 = new Nota();
        nota1.setId(1L);
        Nota nota2 = new Nota();
        nota2.setId(nota1.getId());
        assertThat(nota1).isEqualTo(nota2);
        nota2.setId(2L);
        assertThat(nota1).isNotEqualTo(nota2);
        nota1.setId(null);
        assertThat(nota1).isNotEqualTo(nota2);
    }
}
