package learn.quotes.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class QuoteRepositoryTest {

    @Autowired
    QuoteRepository repository;

    @Test
    void shouldFind2Quotes() {
        var quotes = repository.findAll();
        assertEquals(2, quotes.size());
    }

    @Test
    void shouldFind1Angelou() {
        var quotes = repository.findByPersonContains("Angelou");
        assertEquals(1, quotes.size());
    }

}