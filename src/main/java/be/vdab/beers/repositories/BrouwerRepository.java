package be.vdab.beers.repositories;

import be.vdab.beers.domain.Brouwer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrouwerRepository {
    private final JdbcTemplate template;
    private final RowMapper<Brouwer> brouwerMapper = (result, rowNum) ->
            new Brouwer(result.getLong("id"), result.getString("naam"));

    public BrouwerRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Brouwer> findAll() {
        var sql = """
                select id, naam
                from brouwers
                order by naam
                """;
        return template.query(sql, brouwerMapper);
    }
}
