package be.vdab.beers.repositories;

import be.vdab.beers.domain.BestelBon;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BestelBonRepository.class)
class BestelBonRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BESTELBONNEN = "bestelbonnen";
    private final BestelBonRepository repository;

    BestelBonRepositoryTest(BestelBonRepository repository) {
        this.repository = repository;
    }

    @Test
    void create() {
        var id = repository.create(
                new BestelBon(0, "test", "test", "test", 7000, "test"));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(BESTELBONNEN, "id = " + id)).isOne();
    }
}