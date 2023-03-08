package learn.solarfarm.domain;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.models.SolarPanel;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
public class SolarPanelService {
    public final static int MAX_ROW_COLUMN = 250;

    private final SolarPanelRepository repository;

    public SolarPanelService(SolarPanelRepository repository) {
        this.repository = repository;
    }

    public static int getMaxInstallationYear() {
        return Year.now().getValue();
    }

    public List<SolarPanel> findBySection(String section) throws DataAccessException {
        return repository.findBySection(section);
    }

    public SolarPanel findById(UUID id) throws DataAccessException {
        return repository.findById(id);
    }

    public SolarPanelResult create(SolarPanel solarPanel) throws DataAccessException {
        SolarPanelResult result = validate(solarPanel);

        if (! result.isSuccess()) {
            return result;
        }

        if (solarPanel.getId() != null) {
            result.addErrorMessage("SolarPanel `id` should not be set.");
        }

        if (result.isSuccess()) {
            solarPanel = repository.create(solarPanel);
            result.setSolarPanel(solarPanel);
        }

        return result;
    }

    public SolarPanelResult update(SolarPanel solarPanel) throws DataAccessException {
        SolarPanelResult result = validate(solarPanel);

        if (solarPanel.getId() == null) {
            result.addErrorMessage("SolarPanel `id` is required.");
        }

        if (result.isSuccess()) {
            if (repository.update(solarPanel)) {
                result.setSolarPanel(solarPanel);
            } else {
                result.addErrorMessage("SolarPanel id %s was not found.", solarPanel.getId());
            }
        }
        return result;
    }

    public SolarPanelResult deleteById(UUID id) throws DataAccessException {
        SolarPanelResult result = new SolarPanelResult();
        if (!repository.deleteById(id)) {
            result.addErrorMessage("SolarPanel %s was not found.", id);
        }
        return result;
    }

    private SolarPanelResult validate(SolarPanel solarPanel) throws DataAccessException {
        SolarPanelResult result = new SolarPanelResult();

        if (solarPanel == null) {
            result.addErrorMessage("SolarPanel cannot be null.");
            return result;
        }

        if (solarPanel.getSection() == null || solarPanel.getSection().isBlank()) {
            result.addErrorMessage("SolarPanel `section` is required.");
        }

        if (solarPanel.getRow() < 1 || solarPanel.getRow() >= MAX_ROW_COLUMN) {
            result.addErrorMessage("SolarPanel `row` must be a positive number less than or equal to %s.", MAX_ROW_COLUMN);
        }

        if (solarPanel.getColumn() < 1 || solarPanel.getColumn() >= MAX_ROW_COLUMN) {
            result.addErrorMessage("SolarPanel `column` must be a positive number less than or equal to %s.", MAX_ROW_COLUMN);
        }

        if (solarPanel.getYearInstalled() > getMaxInstallationYear()) {
            result.addErrorMessage("SolarPanel `yearInstalled` must be in the past.");
        }

        if (solarPanel.getMaterial() == null) {
            result.addErrorMessage("SolarPanel `material` is required.");
        }

        // If everything is successful so far, then check if the combined values
        // of **Section**, **Row**, and **Column** are unique
        if (result.isSuccess()) {
            boolean alreadyExists = repository.findBySection(solarPanel.getSection())
                    .stream()
                    .anyMatch(existing ->
                            // don't compare this solarPanel if the IDs are equal because it is the same one
                            (! existing.getId().equals(solarPanel.getId()) ) &&
                                    // but if the IDs are not equal, then make sure the row and column are not identical
                                    // we already know the section is identical because of the findBySection query.
                                    existing.getRow() == solarPanel.getRow() &&
                                    existing.getColumn() == solarPanel.getColumn());

            // If an existing panel was found for the provided **Section**, **Row**, and **Column** values
            // add an error message if the id values don't match (i.e. they're not the same record).
            if (alreadyExists) {
                result.addErrorMessage("SolarPanel `section`, `row`, and `column` must be unique.");
            }
        }

        return result;
    }
}
