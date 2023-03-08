package learn.solarfarm.data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import learn.solarfarm.models.SolarPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("cassandra")
public class SolarPanelCassandraRepository implements SolarPanelRepository {

    private static final Map<String, PreparedStatement> psMap = new ConcurrentHashMap<>();
    private final SolarPanelCassandraRowMapper solarPanelCassandraRowMapper = new SolarPanelCassandraRowMapper();
    private final CqlTemplate cqlTemplate;
    private final CqlSession cqlSession;

    public SolarPanelCassandraRepository(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
        this.cqlTemplate = new CqlTemplate(cqlSession);
    }

    @Override
    public List<SolarPanel> findBySection(String section) throws DataAccessException {
        // TODO: data-access-exercise
    }

    @Override
    public SolarPanel findById(UUID id) throws DataAccessException {
        PreparedStatement preparedStatement = getPreparedStatement("findById",
                "select id, section, row, column, yearInstalled, material, isTracking from " +
                        "panels_by_id where id = ?");
        BoundStatement boundStatement = preparedStatement.bind(id);
        return cqlTemplate.queryForObject(boundStatement, solarPanelCassandraRowMapper);
    }

    @Override
    public SolarPanel create(SolarPanel solarPanel) throws DataAccessException {

        solarPanel.setId(UUID.randomUUID());

        boolean success = createPanelsById(solarPanel);
        if (success) {
            success = createPanelsBySection(solarPanel);
        }
        if (success) {
            success = createPanelsBySectionRowColumn(solarPanel);
        }
        if (success) {
            return solarPanel;
        } else {
            return null;
        }
    }

    @Override
    public boolean update(SolarPanel solarPanel) throws DataAccessException {
        boolean success = updatePanelsById(solarPanel);
        if (success) {
            success = updatePanelsBySection(solarPanel);
        }
        if (success) {
            success = updatePanelsBySectionRowColumn(solarPanel);
        }

        return success;
    }

    @Override
    public boolean deleteById(UUID id) throws DataAccessException {
        SolarPanel solarPanelToDelete = findById(id);

        String section = solarPanelToDelete.getSection();
        int row = solarPanelToDelete.getRow();
        int column = solarPanelToDelete.getColumn();

        boolean success = deletePanelsById(id);
        if (success) {
            success = deletePanelsBySection(section);
        }
        if (success) {
            success = deletePanelsBySectionRowColumn(section, row, column);
        }

        return success;
    }

    private boolean createPanelsById(SolarPanel solarPanel) {
        String cql = "insert into solar_farm.panels_by_id (id, section, row, column, yearInstalled, material, isTracking) " +
                "values (?,?,?,?,?,?,?);";

        return cqlTemplate.execute(cql,
                solarPanel.getId(),
                solarPanel.getSection(),
                solarPanel.getRow(),
                solarPanel.getColumn(),
                solarPanel.getYearInstalled(),
                solarPanel.getMaterial().name(),
                solarPanel.isTracking());
    }

    private boolean createPanelsBySection(SolarPanel solarPanel) {
        String cql = "insert into solar_farm.panels_by_section (section, row, column, id, yearInstalled, material, isTracking) " +
                "values (?,?,?,?,?,?,?);";

        return cqlTemplate.execute(cql,
                solarPanel.getSection(),
                solarPanel.getRow(),
                solarPanel.getColumn(),
                solarPanel.getId(),
                solarPanel.getYearInstalled(),
                solarPanel.getMaterial().name(),
                solarPanel.isTracking());
    }

    private boolean createPanelsBySectionRowColumn(SolarPanel solarPanel) {
        String cql = "insert into solar_farm.panels_by_section_row_column (section, row, column, id, yearInstalled, material, isTracking) " +
                "values (?,?,?,?,?,?,?);";

        return cqlTemplate.execute(cql,
                solarPanel.getSection(),
                solarPanel.getRow(),
                solarPanel.getColumn(),
                solarPanel.getId(),
                solarPanel.getYearInstalled(),
                solarPanel.getMaterial().name(),
                solarPanel.isTracking());
    }

    private boolean updatePanelsById(SolarPanel solarPanel) {
        // TODO: data-access-exercise
        return false;
    }

    private boolean updatePanelsBySection(SolarPanel solarPanel) {
        // TODO: data-access-exercise
        return false;
    }

    private boolean updatePanelsBySectionRowColumn(SolarPanel solarPanel) {
        // TODO: data-access-exercise
        return false;
    }

    private boolean deletePanelsById(UUID id) {
        // TODO: data-access-exercise
        return false;
    }

    private boolean deletePanelsBySection(String section) {
        // TODO: data-access-exercise
        return false;
    }

    private boolean deletePanelsBySectionRowColumn(String section, int row, int column) {
        // TODO: data-access-exercise
        return false;
    }

    private PreparedStatement getPreparedStatement(String key, String cql) {
        if (! psMap.containsKey(key)) {
            psMap.put(key, cqlSession.prepare(cql));
        }
        return psMap.get(key);
    }
}
