package learn.solarfarm.data;

import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("jdbc")
class SolarPanelJdbcRepositoryTest {

    @Autowired
    private SolarPanelJdbcRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SolarPanel existingSolarPanel;

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("call set_known_good_state();");

        existingSolarPanel = new SolarPanel();
        existingSolarPanel.setId(UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464"));
        existingSolarPanel.setSection("The Ridge");
        existingSolarPanel.setRow(1);
        existingSolarPanel.setColumn(1);
        existingSolarPanel.setYearInstalled(2020);
        existingSolarPanel.setMaterial(Material.POLY_SI);
        existingSolarPanel.setTracking(false);
    }

    @Test
    void shouldFindBySection() throws DataAccessException {
        assertEquals(3, repository.findBySection("The Ridge").size());
    }

    @Test
    void shouldNotFindByMissingSection() throws DataAccessException {
        assertEquals(0, repository.findBySection("XYZ").size());
    }

    @Test
    void shouldFindById() throws DataAccessException {
        SolarPanel solarPanel = repository.findById(UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464"));
        assertNotNull(solarPanel);
        assertEquals(existingSolarPanel, solarPanel);
    }

    @Test
    void shouldNotFindByIdThatDoesNotExist() throws DataAccessException {
        SolarPanel solarPanel = repository.findById(UUID.randomUUID());
        assertNull(solarPanel);
    }

    @Test
    void shouldCreate() throws DataAccessException {
        SolarPanel newSolarPanel = existingSolarPanel;
        existingSolarPanel.setSection("New Section");
        SolarPanel added = repository.create(newSolarPanel);
        assertNotNull(added);
        assertNotNull(added.getId());
    }

    @Test
    void shouldNotCreateDuplicate() throws DataAccessException {
        SolarPanel newSolarPanel = existingSolarPanel;
        assertThrows(DuplicateKeyException.class, () -> repository.create(newSolarPanel));
    }

    @Test
    void shouldDeleteById() throws DataAccessException {
        boolean deleted = repository.deleteById(UUID.fromString("e5ba5bd8-3ccb-4a33-ab9f-522871314464"));
        assertTrue(deleted);
    }

    @Test
    void shouldNotDeleteByIdThatDoesNotExist() throws DataAccessException {
        boolean deleted = repository.deleteById(UUID.randomUUID());
        assertFalse(deleted);
    }

}