package be.vdab.beers.repositories;

import be.vdab.beers.domain.Bier;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class BierRepository {
    private final JdbcTemplate template;
    private final RowMapper<Bier> bierMapper = (result, rowNum) -> new Bier(result.getLong("id"),
            result.getString("naam"), result.getLong("brouwerId"),
            result.getBigDecimal("alcohol"), result.getBigDecimal("prijs"));

    public BierRepository(JdbcTemplate template) {
        this.template = template;
    }

    public long findAantal() {
        var sql = """
                select count(*)
                from bieren
                """;
        return template.queryForObject(sql, Long.class);
    }

    public List<Bier> findByBrouwer(long brouwerId) {
        var sql = """
                select id, naam, brouwerId, alcohol, prijs
                from bieren
                where brouwerId = ?
                order by naam
                """;
        return template.query(sql, bierMapper, brouwerId);
    }

    public Optional<Bier> findById(long id) {
        try {
            var sql = """
                    select id, naam, brouwerId, alcohol, prijs
                    from bieren
                    where id = ?
                    """;
            return Optional.ofNullable(template.queryForObject(sql, bierMapper, id));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }


    public Optional<Bier> findByIdForUpdate(long id) {
        try {
            var sql = """
                    select id, naam, brouwerId, alcohol, prijs
                    from bieren
                    where id = ?
                    for update
                    """;
            return Optional.ofNullable(template.queryForObject(sql, bierMapper, id));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Bier> findByIds(Set<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var sql = """
                select id, naam, brouwerId, alcohol, prijs
                from bieren
                where id in (
                """ + "?,".repeat(ids.size() - 1) + "?) order by id";
        return template.query(sql, bierMapper, ids.toArray());
    }

    public List<Bier> findByIdsForUpdate(Set<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var sql = """
                select id, naam, brouwerId, alcohol, prijs
                from bieren
                where id in (
                """ + "?,".repeat(ids.size() - 1) + "?) order by id for update";
        return template.query(sql, bierMapper, ids.toArray());
    }

    public void verhoogAantal(long id, int aantal) {
        var sql = """
                update bieren
                set besteld = besteld + ?
                where id = ?
                """;
        template.update(sql, aantal, id);
    }
}
