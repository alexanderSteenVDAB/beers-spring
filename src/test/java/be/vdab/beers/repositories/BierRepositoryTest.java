package be.vdab.beers.repositories;

import be.vdab.beers.domain.Bier;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BierRepository.class)
@Sql({"/insertBrouwer.sql", "/insertBier.sql"})
class BierRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BIEREN = "bieren";
    private final BierRepository repository;

    BierRepositoryTest(BierRepository repository) {
        this.repository = repository;
    }

    private long idVanTestBrouwer() {
        return jdbcTemplate.queryForObject("select id from brouwers where naam = 'test'", Long.class);
    }

    private long idVanTestBier() {
        return jdbcTemplate.queryForObject("select id from bieren where naam = 'test'", Long.class);
    }

    private long idVanTestBier2() {
        return jdbcTemplate.queryForObject("select id from bieren where naam = 'test2'", Long.class);
    }

    @Test
    void findAantal() {
        assertThat(repository.findAantal()).isEqualTo(countRowsInTable(BIEREN));
    }


    @Test
    void findByBrouwer() {
        assertThat(repository.findByBrouwer(idVanTestBrouwer()))
                .hasSize(jdbcTemplate.queryForObject("select count(*) from bieren where brouwerId = "
                        + idVanTestBrouwer(), Integer.class))
                .allSatisfy(bier -> assertThat(bier.getBrouwerId()).isEqualByComparingTo(idVanTestBrouwer()))
                .extracting(Bier::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test
    void findByBrouwerMetNietBestaandeBrouwer() {
        assertThat(repository.findByBrouwer(-1)).isEmpty();
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestBier()))
                .hasValueSatisfying(bier -> assertThat(bier.getNaam()).isEqualTo("test"));
    }

    @Test
    void findByOnbestaandeIdVindtGeenBier() {
        assertThat(repository.findById(-1)).isEmpty();
    }


    @Test
    void findByIdForUpdate() {
        assertThat(repository.findByIdForUpdate(idVanTestBier()))
                .hasValueSatisfying(bier -> assertThat(bier.getNaam()).isEqualTo("test"));
    }

    @Test
    void findByIdForUpdateOnbestaandeId() {
        assertThat(repository.findByIdForUpdate(-1)).isEmpty();
    }


    @Test
    void findByIds() {
        long id1 = idVanTestBier();
        long id2 = idVanTestBier2();
        assertThat(repository.findByIds(Set.of(id1, id2)))
                .extracting(Bier::getId)
                .containsOnly(id1, id2)
                .isSorted();
    }

    @Test
    void findByIdsGeeftLegeVerzamelingBierenBijLegeVerzamelingIds() {
        assertThat(repository.findByIds(Set.of())).isEmpty();
    }

    @Test
    void findByIdsGeeftLegeVerzamelingBierenBijOnbestaandeIds() {
        assertThat(repository.findByIds(Set.of(-1L))).isEmpty();
    }

    @Test
    void findByIdsForUpdate() {
        long id1 = idVanTestBier();
        long id2 = idVanTestBier2();
        assertThat(repository.findByIdsForUpdate(Set.of(id1, id2)))
                .extracting(Bier::getId)
                .containsOnly(id1, id2)
                .isSorted();
    }

    @Test
    void findByIdsForUpdateGeeftLegeVerzamelingBierenBijLegeVerzamelingIds() {
        assertThat(repository.findByIdsForUpdate(Set.of())).isEmpty();
    }

    @Test
    void findByIdsForUpdateGeeftLegeVerzamelingBierenBijOnbestaandeIds() {
        assertThat(repository.findByIdsForUpdate(Set.of(-1L))).isEmpty();
    }

    @Test
    void verhoogAantal() {
        repository.verhoogAantal(idVanTestBier(), 10);
        assertThat(jdbcTemplate.queryForObject(
                "select besteld from bieren where id = " + idVanTestBier(), Integer.class)).isEqualTo(20);
    }
}