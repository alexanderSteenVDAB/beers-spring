package be.vdab.beers.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BestelBonLijnTest {

    @Test
    void getPrijs() {
        var bier = new Bier(1L, "test", 1L, BigDecimal.TEN, BigDecimal.ONE);
        var bestelBonLijn = new BestelBonLijn(bier, 10);
        assertThat(bestelBonLijn.getPrijs()).isEqualByComparingTo("10");
    }
}