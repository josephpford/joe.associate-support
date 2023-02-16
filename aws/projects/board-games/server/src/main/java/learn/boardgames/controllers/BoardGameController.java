package learn.boardgames.controllers;

import learn.boardgames.domain.BoardGameService;
import learn.boardgames.domain.Result;
import learn.boardgames.domain.ResultType;
import learn.boardgames.models.BoardGame;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board-game")
@CrossOrigin(origins = { "http://localhost:3000" })
public class BoardGameController {

    private final BoardGameService service;

    public BoardGameController(BoardGameService service) {
        this.service = service;
    }

    @GetMapping
    public List<BoardGame> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGame> findById(@PathVariable int id) {
        BoardGame game = service.findById(id);
        if (game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody BoardGame game) {
        Result result = service.add(game);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody BoardGame game) {
        if (id != game.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result result = service.update(game);
        if (result.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (result.getResultType() == ResultType.INVALID) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        Result result = service.deleteById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
