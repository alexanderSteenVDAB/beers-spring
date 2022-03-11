package be.vdab.beers.repositories;

import be.vdab.beers.domain.BestelBon;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BestelBonRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<BestelBon> bestelBonMapper = (result, rowNum) ->
            new BestelBon(result.getLong("id"), result.getString("naam"),
                    result.getString("straat"), result.getString("huisnr"),
                    result.getInt("postcode"), result.getString("gemeente"));

    public BestelBonRepository(JdbcTemplate template) {
        this.template = template;
        insert = new SimpleJdbcInsert(template)
                .withTableName("bestelbonnen")
                .usingGeneratedKeyColumns("id");
    }

    public long create(BestelBon bestelBon) {
        return insert.executeAndReturnKey(
                        Map.of("naam", bestelBon.getNaam(),
                                "straat", bestelBon.getStraat(),
                                "huisnr", bestelBon.getHuisnr(),
                                "postcode", bestelBon.getPostcode(),
                                "gemeente", bestelBon.getGemeente()))
                .longValue();
    }
}
