package be.vdab.beers.services;

import be.vdab.beers.domain.BestelBon;
import be.vdab.beers.domain.BestelBonLijn;
import be.vdab.beers.domain.Bier;
import be.vdab.beers.repositories.BestelBonLijnRepository;
import be.vdab.beers.repositories.BestelBonRepository;
import be.vdab.beers.sessions.Mandje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MandjeServiceTest {
    private MandjeService service;
    @Mock
    private Mandje mandje;
    @Mock
    private BierService bierService;
    @Mock
    private BestelBonRepository bestelBonRepository;
    @Mock
    private BestelBonLijnRepository bestelBonLijnRepository;

    @BeforeEach
    void beforeEach() {
        service = new MandjeService(mandje, bierService, bestelBonRepository, bestelBonLijnRepository);
        when(mandje.getBesteldeArtikels())
                .thenReturn(Map.of(1L, 10, 3L, 30, 2L, 20, 4L, 40));
        when(bierService.findById(1L)).thenReturn(
                Optional.of(new Bier(1L, "BBB", 1L, BigDecimal.TEN, BigDecimal.ONE)));
        when(bierService.findById(2L)).thenReturn(
                Optional.of(new Bier(2L, "CCC", 1L, BigDecimal.TEN, BigDecimal.ONE)));
        when(bierService.findById(3L)).thenReturn(
                Optional.of(new Bier(3L, "AAA", 1L, BigDecimal.TEN, BigDecimal.ONE)));
        when(bierService.findById(4L)).thenReturn(Optional.empty());
    }

    @AfterEach
    void afterEach() {
        verify(mandje).getBesteldeArtikels();
        verify(bierService, atLeastOnce()).findById(1L);
        verify(bierService, atLeastOnce()).findById(2L);
        verify(bierService, atLeastOnce()).findById(3L);
        verify(bierService, atLeastOnce()).findById(4L);
    }

    @Test
    void findBestelBonLijnen() {
        var bestelBonLijnen = service.findBestelBonLijnen();
        assertThat(bestelBonLijnen).contains(
                new BestelBonLijn(new Bier(1L, "BBB", 1L, BigDecimal.TEN, BigDecimal.ONE), 10),
                new BestelBonLijn(new Bier(2L, "CCC", 1L, BigDecimal.TEN, BigDecimal.ONE), 20),
                new BestelBonLijn(new Bier(3L, "AAA", 1L, BigDecimal.TEN, BigDecimal.ONE), 30)
        );
    }

    @Test
    void berekenTotaal() {
        assertThat(service.berekenTotaal()).isEqualByComparingTo("60");
    }

    @Test
    void bevestig() {
    }

    @Test
    void bevestigGeetJuistIdTerug() {
        var bestelBon = new BestelBon(1L, "test", "test", "test", 7000, "test");
        when(bestelBonRepository.create(bestelBon)).thenReturn(1L);
        assertThat(service.bevestig(bestelBon)).isEqualTo(1L);
    }


    @Test
    void getBierNamen() {
        assertThat(service.getBierNamen()).contains("AAA", "BBB", "CCC").isSorted();
    }

}