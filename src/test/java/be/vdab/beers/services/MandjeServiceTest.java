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
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                .thenReturn(Map.of(1L, 10, 3L, 30, 2L, 20, 4L, 40)); // todo niet meer voor nulle testen


    }

    @AfterEach
    void afterEach() {
        verify(mandje, atLeastOnce()).getBesteldeArtikels();
    }

    @Test
    void findBestelBonLijnen() {
        when(bierService.findByIds(Set.of(1L, 3L, 2L, 4L))).thenReturn(List.of(
                new Bier(1L, "BBB", 1L, BigDecimal.TEN, BigDecimal.ONE),
                new Bier(3L, "AAA", 1L, BigDecimal.TEN, BigDecimal.ONE),
                new Bier(2L, "CCC", 1L, BigDecimal.TEN, BigDecimal.ONE)
        ));
        var bestelBonLijnen = service.findBestelBonLijnen();
        assertThat(bestelBonLijnen).containsExactly(
                new BestelBonLijn(new Bier(1L, "BBB", 1L, BigDecimal.TEN, BigDecimal.ONE), 10),
                new BestelBonLijn(new Bier(3L, "AAA", 1L, BigDecimal.TEN, BigDecimal.ONE), 30),
                new BestelBonLijn(new Bier(2L, "CCC", 1L, BigDecimal.TEN, BigDecimal.ONE), 20)
        );
        verify(bierService).findByIds(Set.of(1L, 3L, 2L, 4L));
    }


    @Test
    void bevestigGeetJuistIdTerug() {
        when(bierService.findByIdsForUpdate(Set.of(1L, 3L, 2L, 4L))).thenReturn(List.of(
                new Bier(1L, "BBB", 1L, BigDecimal.TEN, BigDecimal.ONE),
                new Bier(3L, "AAA", 1L, BigDecimal.TEN, BigDecimal.ONE),
                new Bier(2L, "CCC", 1L, BigDecimal.TEN, BigDecimal.ONE)
        ));

        var bestelBon = new BestelBon(1L, "test", "test", "test", 7000, "test");
        when(bestelBonRepository.create(bestelBon)).thenReturn(1L);

        assertThat(service.bevestig(bestelBon)).isEqualTo(1L);

        verify(bierService).findByIdsForUpdate(Set.of(1L, 3L, 2L, 4L));
        verify(bestelBonRepository).create(bestelBon);
    }


    @Test
    void getBierNamen() {
        when(bierService.findByIds(Set.of(1L, 3L, 2L, 4L))).thenReturn(List.of(
                new Bier(1L, "BBB", 1L, BigDecimal.TEN, BigDecimal.ONE),
                new Bier(3L, "AAA", 1L, BigDecimal.TEN, BigDecimal.ONE),
                new Bier(2L, "CCC", 1L, BigDecimal.TEN, BigDecimal.ONE)
        ));

        assertThat(service.getBierNamen()).contains("AAA", "BBB", "CCC").isSorted();

        verify(bierService).findByIds(Set.of(1L, 3L, 2L, 4L));
    }

}