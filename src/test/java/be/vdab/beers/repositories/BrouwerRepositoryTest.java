package be.vdab.beers.repositories;

import be.vdab.beers.domain.Brouwer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BrouwerRepository.class)
@Sql("/insertBrouwer.sql")
class BrouwerRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BROUWERS = "brouwers";
    private final BrouwerRepository repository;

    BrouwerRepositoryTest(BrouwerRepository repository) {
        this.repository = repository;
    }

    @Test
    void findAll() {
        assertThat(repository.findAll())
                .hasSize(countRowsInTable(BROUWERS))
                .extracting(Brouwer::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

}
