package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    public AccidentJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Rule> ruleRowMapper = (resultSet, rowNum) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    };

    private final RowMapper<AccidentType> typeRowMapper = (resultSet, rowNum) -> {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(resultSet.getInt("id"));
        accidentType.setName(resultSet.getString("name"));
        return accidentType;
    };

    private final RowMapper<Accident> accidentRowMapper = (resultSet, rowNum) -> {
        Accident accident = new Accident();
        int accidentId = resultSet.getInt("a_id");
        accident.setId(accidentId);
        accident.setName(resultSet.getString("a_name"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));

        AccidentType accidentType = new AccidentType();
        accidentType.setId(resultSet.getInt("t_id"));
        accidentType.setName(resultSet.getString("t_name"));
        accident.setType(accidentType);

        List<Rule> rules = getRulesByAccidentId(accidentId);
        accident.setRules(new HashSet<>(rules));
        return accident;
    };

    public void saveAccident(Accident accident) {
        if (accident.getId() == 0) {
            insertAccident(accident);
        } else {
            updateAccident(accident);
        }
    }

    public List<Accident> getAllAccidents() {
        return jdbcTemplate.query(
                "select a.id a_id, a.name a_name, a.text, a.address, t.id t_id, t.name t_name from " +
                        "accidents a inner join " +
                        "accident_types t " +
                        "on a.type_id = t.id",
                accidentRowMapper
        );
    }

    public Accident getAccidentById(int id) {
        return jdbcTemplate.queryForObject(
                "select a.id a_id, a.name a_name, a.text, a.address, t.id t_id, t.name t_name from " +
                        "accidents a inner join " +
                        "accident_types t " +
                        "on a.type_id = t.id " +
                        "where a.id = ?",
                accidentRowMapper,
                id
        );
    }

    public List<Rule> getRulesByAccidentId(int accidentId) {
        return jdbcTemplate.query(
                "select r.id, r.name from " +
                        "rules r inner join " +
                        "accidents_rules ar " +
                        "on ar.rule_id = r.id " +
                        "where ar.accident_id = ?",
                ruleRowMapper,
                accidentId
        );
    }

    public List<AccidentType> getAllAccidentTypes() {
        return jdbcTemplate.query(
                "select id, name from accident_types", typeRowMapper
        );
    }

    public AccidentType getAccidentTypeById(int id) {
        return jdbcTemplate.queryForObject(
                "select id, name from accident_types where id = ?", typeRowMapper, id
        );
    }

    public List<Rule> getAllRules() {
        return jdbcTemplate.query("select id, name from rules", ruleRowMapper);
    }

    public Rule getRuleById(int id) {
        return jdbcTemplate.queryForObject(
                "select id, name from rules where id = ?", ruleRowMapper, id
        );
    }

    private void insertAccident(Accident accident) {
        String INSERT_SQL = "insert into accidents (name, text, address, type_id) values(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        int accidentId = keyHolder.getKey().intValue();
        for (Rule rule : accident.getRules()) {
            jdbcTemplate.update(
                    "insert into accidents_rules (accident_id, rule_id) values (?, ?)",
                    accidentId,
                    rule.getId()
            );
        }
    }

    private void updateAccident(Accident accident) {
        jdbcTemplate.update(
                "update accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );
        jdbcTemplate.update(
                "delete from accidents_rules where accident_id = ?",
                accident.getId()
        );
        for (Rule rule :accident.getRules()) {
            jdbcTemplate.update(
                    "insert into accidents_rules (accident_id, rule_id) values (?, ?)",
                    accident.getId(),
                    rule.getId()
            );
        }
    }
}
