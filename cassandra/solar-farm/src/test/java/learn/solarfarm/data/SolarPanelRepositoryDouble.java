package learn.solarfarm.data;

import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SolarPanelRepositoryDouble implements SolarPanelRepository {
    private final ArrayList<SolarPanel> solarPanels = new ArrayList<>();

    public SolarPanelRepositoryDouble() {
        solarPanels.add(new SolarPanel(UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464"), "Section One", 1, 1, 2020, Material.POLY_SI, true));
        solarPanels.add(new SolarPanel(UUID.fromString("4fc6d67b-d885-4739-bebc-9bdb18de55e8"), "Section One", 1, 2, 2020, Material.POLY_SI, true));
        solarPanels.add(new SolarPanel(UUID.fromString("372a5d19-7e40-4665-8b4f-a9f380f61a36"), "Section Two", 10, 11, 2000, Material.A_SI, false));
    }

    @Override
    public List<SolarPanel> findBySection(String section) throws DataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        for (SolarPanel sp : solarPanels) {
            if (sp.getSection().equalsIgnoreCase(section)) {
                result.add(sp);
            }
        }
        return result;
    }

    @Override
    public SolarPanel findById(UUID id) throws DataAccessException {
        for (SolarPanel sp : solarPanels) {
            if (sp.getId().equals(id)) {
                return sp;
            }
        }
        return null;
    }

    @Override
    public SolarPanel create(SolarPanel solarPanel) throws DataAccessException {
        return solarPanel;
    }

    @Override
    public boolean update(SolarPanel solarPanel) throws DataAccessException {
        SolarPanel existingSolarPanel = null;
        for (SolarPanel sp : solarPanels) {
            if (sp.getId().equals(solarPanel.getId())) {
                existingSolarPanel = sp;
            }
        }
        return existingSolarPanel != null;
    }

    @Override
    public boolean deleteById(UUID id) throws DataAccessException {
        return findById(id) != null;
    }
}
