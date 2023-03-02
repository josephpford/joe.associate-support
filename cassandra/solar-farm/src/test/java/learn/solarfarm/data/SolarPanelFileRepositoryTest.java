package learn.solarfarm.data;

import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/file/solarfarm-seed.txt";
    static final String TEST_FILE_PATH = "./data/file/solarfarm-test.txt";

    final SolarPanelFileRepository repository = new SolarPanelFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findBySection() throws DataAccessException {
        // test that the "The Ridge" section returns two solar panels
        List<SolarPanel> actual = repository.findBySection("The Ridge");
        assertEquals(2, actual.size());

        // test that capitalization doesn't matter
        actual = repository.findBySection("THE RIDGE");
        assertEquals(2, actual.size());

        // test that an empty list returned if a section doesn't have any solar panels
        actual = repository.findBySection("East Hill");
        assertEquals(0, actual.size());
    }

    @Test
    void findById() throws DataAccessException {
        // test that an existing solar panel can be retrieved
        SolarPanel solarPanel = repository.findById(UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464"));
        assertSolarPanelFieldValues(solarPanel, UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464"), "The Ridge", 1, 1, 2020,
                Material.POLY_SI, true);

        // test that attempting to retrieve an non-existent solar panel returns null
        solarPanel = repository.findById(UUID.randomUUID());
        assertNull(solarPanel);
    }

    @Test
    void create() throws DataAccessException {
        SolarPanel solarPanel = new SolarPanel();
        solarPanel.setSection("The Ridge");
        solarPanel.setRow(1);
        solarPanel.setColumn(10);
        solarPanel.setYearInstalled(2020);
        solarPanel.setMaterial(Material.A_SI);
        solarPanel.setTracking(true);

        SolarPanel actual = repository.create(solarPanel);
        assertNotNull(actual.getId());

        List<SolarPanel> all = repository.findBySection("The Ridge");
        assertEquals(3, all.size());

        // the newly-added solar panel
        actual = all.get(2);
        UUID newUuid = actual.getId();
        assertSolarPanelFieldValues(actual, newUuid, "The Ridge", 1, 10, 2020,
                Material.A_SI, true);
    }

    @Test
    void update() throws DataAccessException {
        SolarPanel solarPanel = repository.findById(UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464"));
        solarPanel.setSection("East Hill");           // was The Ridge
        solarPanel.setTracking(false);                // was true
        assertTrue(repository.update(solarPanel));

        solarPanel = repository.findById(UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464"));
        assertNotNull(solarPanel);                                     // confirm the solar panel exists
        assertEquals("East Hill", solarPanel.getSection());    // confirm the solar panel was updated
        assertFalse(solarPanel.isTracking());

        SolarPanel doesNotExist = new SolarPanel();
        doesNotExist.setId(UUID.randomUUID());
        assertFalse(repository.update(doesNotExist)); // can't update a solar panel that doesn't exist
    }

    @Test
    void deleteById() throws DataAccessException {
        int count = repository.findBySection("The Ridge").size();
        assertTrue(repository.deleteById(UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464")));
        assertFalse(repository.deleteById(UUID.randomUUID()));
        assertEquals(count - 1, repository.findBySection("The Ridge").size());
    }

    private void assertSolarPanelFieldValues(
            SolarPanel solarPanel, UUID id, String section, int row, int column, int yearInstalled,
            Material material, boolean isTracking) {
        assertNotNull(solarPanel);
        assertEquals(id, solarPanel.getId());
        assertEquals(section, solarPanel.getSection());
        assertEquals(row, solarPanel.getRow());
        assertEquals(column, solarPanel.getColumn());
        assertEquals(yearInstalled, solarPanel.getYearInstalled());
        assertEquals(material, solarPanel.getMaterial());
        assertEquals(isTracking, solarPanel.isTracking());
    }
}
