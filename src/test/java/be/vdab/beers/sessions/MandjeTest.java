package be.vdab.beers.sessions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MandjeTest {
    private Mandje mandje;

    @BeforeEach
    void beforeEach() {
        mandje = new Mandje();
    }

    @Test
    void eenNieuwMandjeIsLeeg() {
        assertThat(mandje.getBesteldeArtikels()).isEmpty();
    }

    @Test
    void voegToe1Artikel() {
        mandje.voegToe(10L, 10);
        assertThat(mandje.getBesteldeArtikels().keySet()).containsOnly(10L);
    }

    @Test
    void voegToeZelfdeArtikelVerhoogdAantal() {
        mandje.voegToe(10L, 10);
        mandje.voegToe(10L, 10);
        assertThat(mandje.getBesteldeArtikels().get(10L)).isEqualTo(20);
    }

    @Test
    void voegToe2Artikels() {
        mandje.voegToe(10L, 10);
        mandje.voegToe(20L, 10);
        assertThat(mandje.getBesteldeArtikels().keySet()).containsOnly(10L, 20L);
    }


}