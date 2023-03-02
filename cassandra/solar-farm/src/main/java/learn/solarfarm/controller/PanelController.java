package learn.solarfarm.controller;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.SolarPanelResult;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.SolarPanel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/panel")
public class PanelController {

    private final SolarPanelService service;

    public PanelController(SolarPanelService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable UUID id) throws DataAccessException {
        SolarPanel solarPanel = service.findById(id);
        if (solarPanel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(solarPanel, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody SolarPanel solarPanel) throws DataAccessException {
        SolarPanelResult result = service.create(solarPanel);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getSolarPanel(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody SolarPanel solarPanel) throws DataAccessException {
        if (! solarPanel.getId().equals(id)) {
            return new ResponseEntity<>("ID of path must match ID of requestBody", HttpStatus.BAD_REQUEST);
        }
        SolarPanelResult result = service.update(solarPanel);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getSolarPanel(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) throws DataAccessException {
        SolarPanelResult result = service.deleteById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getSolarPanel(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

}
