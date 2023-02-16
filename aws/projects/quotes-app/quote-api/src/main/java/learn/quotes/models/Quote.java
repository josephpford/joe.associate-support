package learn.quotes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quoteId;
    @Size(min = 1, max = 1024, message = "Phrase is required and can be no longer than 1024 characters.")
    private String phrase;
    @Size(min = 1, max = 128, message = "Person is required and can be no longer than 128 characters.")
    private String person;
    @Size(max = 512, message = "Person Image URL can be no longer than 512 characters.")
    private String personImageUrl;

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPersonImageUrl() {
        return personImageUrl;
    }

    public void setPersonImageUrl(String personImageUrl) {
        this.personImageUrl = personImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return quoteId == quote.quoteId && Objects.equals(phrase, quote.phrase) && Objects.equals(person, quote.person) && Objects.equals(personImageUrl, quote.personImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quoteId, phrase, person, personImageUrl);
    }
}
