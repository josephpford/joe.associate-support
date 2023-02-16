package learn.quotes.controllers;

import learn.quotes.data.QuoteRepository;
import learn.quotes.models.Quote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteRepository repository;

    public QuoteController(QuoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{quoteId}")
    public ResponseEntity<Quote> get(@PathVariable int quoteId) {
        var quote = repository.findById(quoteId).orElse(null);
        if (quote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quote, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Quote>> find(@RequestParam(required = false) String person) {
        if (person == null || person.isBlank()) {
            return new ResponseEntity<>(
                    repository.findAll(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                repository.findByPersonContains(person),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody @Valid Quote quote, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(oe -> oe.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }
        quote = repository.save(quote);
        return new ResponseEntity<>(quote, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> put(@RequestBody @Valid Quote quote, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(oe -> oe.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }
        repository.save(quote);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{quoteId}")
    public ResponseEntity<?> delete(@PathVariable int quoteId) {
        if (!repository.existsById(quoteId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(quoteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
