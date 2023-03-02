package learn.solarfarm.controller;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.SolarPanel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/section")
public class SectionController<I> {

    private final SolarPanelService service;

    public SectionController(SolarPanelService service) {
        this.service = service;
    }

    @GetMapping("/{section}")
    public ResponseEntity<List<SolarPanel>> findBySection(@PathVariable String section) throws DataAccessException {
        List<SolarPanel> found = service.findBySection(section);
        if (found == null || found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
    }

}
