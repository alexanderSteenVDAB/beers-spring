package be.vdab.beers.repositories;

import be.vdab.beers.domain.BestelBonLijn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BestelBonLijnRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    public BestelBonLijnRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template)
                .withTableName("bestelbonlijnen");
    }

    public void create(BestelBonLijn bestelBonLijn) {
        insert.execute(Map.of("bestelbonId", bestelBonLijn.getBestelBonId(),
                "bierId", bestelBonLijn.getBierId(),
                "aantal", bestelBonLijn.getAantal(),
                "prijs", bestelBonLijn.getPrijs()));
    }
}
