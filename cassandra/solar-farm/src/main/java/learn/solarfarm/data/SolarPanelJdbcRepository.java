package learn.solarfarm.data;

import learn.solarfarm.models.SolarPanel;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("jdbc")
public class SolarPanelJdbcRepository implements SolarPanelRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SolarPanelRowMapper rowMapper = new SolarPanelRowMapper();

    public SolarPanelJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SolarPanel> findBySection(String section) throws DataAccessException {
        String sql = "select `id`, `section`, `row`, `column`, " +
                "`year_installed`, `material`, `is_tracking` from solar_panel where section = ?;";
        return jdbcTemplate.query(sql, rowMapper, section);
    }

    @Override
    public SolarPanel findById(UUID id) throws DataAccessException {
        String sql = "select `id`, `section`, `row`, `column`, " +
                "`year_installed`, `material`, `is_tracking` from `solar_panel` where `id` = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id.toString());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public SolarPanel create(SolarPanel solarPanel) throws DataAccessException {
        String sql = "insert into solar_panel (`id`, `section`, `row`, `column`, " +
                "`year_installed`, `material`, `is_tracking`) values (?,?,?,?,?,?,?);";

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, solarPanel.getId().toString());
            ps.setString(2, solarPanel.getSection());
            ps.setInt(3, solarPanel.getRow());
            ps.setInt(4, solarPanel.getColumn());
            ps.setInt(5, solarPanel.getYearInstalled());
            ps.setString(6, solarPanel.getMaterial().name());
            ps.setBoolean(7, solarPanel.isTracking());

            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }

        return solarPanel;
    }

    @Override
    public boolean update(SolarPanel solarPanel) throws DataAccessException {
        String sql = "update solar_panel set section = ?, `row` = ?, `column` = ?, year_installed = ?, material = ?, is_tracking = ? where id = ?;";

        int rowsAffected = jdbcTemplate.update(sql,
                solarPanel.getSection(),
                solarPanel.getRow(),
                solarPanel.getColumn(),
                solarPanel.getYearInstalled(),
                solarPanel.getMaterial().name(),
                solarPanel.isTracking(),
                solarPanel.getId().toString());

        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(UUID id) throws DataAccessException {
        String sql = "delete from solar_panel where id = ?;";
        return jdbcTemplate.update(sql, id.toString()) > 0;
    }

}