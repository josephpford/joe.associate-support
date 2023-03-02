package learn.solarfarm.data;

import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SolarPanelRowMapper implements RowMapper<SolarPanel> {

    @Override
    public SolarPanel mapRow(ResultSet rs, int rowNum) throws SQLException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setId(UUID.fromString(rs.getString("id")));
        solarPanel.setSection(rs.getString("section"));
        solarPanel.setRow(rs.getInt("row"));
        solarPanel.setColumn(rs.getInt("column"));
        solarPanel.setYearInstalled(rs.getInt("year_installed"));
        solarPanel.setMaterial(Material.valueOf(rs.getString("material")));
        solarPanel.setTracking(rs.getBoolean("is_tracking"));
        return solarPanel;
    }

}
