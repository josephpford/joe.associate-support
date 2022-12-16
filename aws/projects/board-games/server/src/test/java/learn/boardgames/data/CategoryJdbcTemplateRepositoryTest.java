package learn.boardgames.data;

import learn.boardgames.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CategoryJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryJdbcTemplateRepository repository;

    @Test
    void shouldFindAll() {
        List<Category> categories = repository.findAll();

        assertEquals(8, categories.size());
    }
}