package learn.quotes.data;

import learn.quotes.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {

    List<Quote> findByPersonContains(String partial);
}
