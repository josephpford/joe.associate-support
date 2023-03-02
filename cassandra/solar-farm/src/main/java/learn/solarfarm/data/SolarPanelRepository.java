package learn.solarfarm.data;

import learn.solarfarm.models.SolarPanel;

import java.util.List;
import java.util.UUID;

public interface SolarPanelRepository {
    List<SolarPanel> findBySection(String section) throws DataAccessException;

    SolarPanel findById(UUID id) throws DataAccessException;

    SolarPanel create(SolarPanel solarPanel) throws DataAccessException;

    boolean update(SolarPanel solarPanel) throws DataAccessException;

    boolean deleteById(UUID id) throws DataAccessException;
}
