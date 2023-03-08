package learn.solarfarm.data;

import com.datastax.oss.driver.api.core.DriverException;
import com.datastax.oss.driver.api.core.cql.Row;
import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;
import org.springframework.data.cassandra.core.cql.RowMapper;

import java.util.UUID;

public class SolarPanelCassandraRowMapper implements RowMapper<SolarPanel> {

    @Override
    public SolarPanel mapRow(Row row, int rowNum) throws DriverException {
        SolarPanel solarPanel = new SolarPanel();

        solarPanel.setId(row.get("id", UUID.class));
        solarPanel.setSection(row.get("section", String.class));
        solarPanel.setRow(row.get("row", Integer.class));
        solarPanel.setColumn(row.get("column", Integer.class));
        solarPanel.setYearInstalled(row.get("yearInstalled", Integer.class));
        solarPanel.setMaterial(Material.valueOf(row.get("material", String.class)));
        solarPanel.setTracking(row.get("isTracking", Boolean.class));

        return solarPanel;
    }
}
