package be.vdab.beers.repositories;

import be.vdab.beers.domain.BestelBonLijn;
import be.vdab.beers.domain.Bier;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({BestelBonLijnRepository.class, BierRepository.class})
@Sql({"/insertBrouwer.sql", "/insertBier.sql", "/insertBestelBon.sql"})
class BestelBonLijnRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BESTELBONLIJNEN = "bestelbonlijnen";
    private final BestelBonLijnRepository repository;
    private final BierRepository bierRepository;

    BestelBonLijnRepositoryTest(BestelBonLijnRepository repository, BierRepository bierRepository) {
        this.repository = repository;
        this.bierRepository = bierRepository;
    }

    private long idVanTestBrouwer() {
        return jdbcTemplate.queryForObject("select id from brouwers where naam = 'test'", Long.class);
    }

    private long idVanTestBier() {
        return jdbcTemplate.queryForObject("select id from bieren where naam = 'test'", Long.class);
    }

    private long idVanTestBestelBon() {
        return jdbcTemplate.queryForObject("select id from bestelbonnen where naam = 'test'", Long.class);
    }

    private Bier getTestBier() {
        var bd10 = BigDecimal.TEN;
        return new Bier(idVanTestBier(), "test", idVanTestBrouwer(), bd10, bd10);
    }

    @Test
    void create() {
        repository.create(new BestelBonLijn(idVanTestBestelBon(), getTestBier(), 10));
        assertThat(countRowsInTableWhere(BESTELBONLIJNEN,
                "bestelbonId = " + idVanTestBestelBon() + " and bierId = " + idVanTestBier())).isOne();
    }
}