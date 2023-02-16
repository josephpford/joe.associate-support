package learn.boardgames.domain;

import learn.boardgames.data.CategoryRepository;
import learn.boardgames.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CategoryServiceTest {

    @MockBean
    CategoryRepository repository;

    @Autowired
    CategoryService service;

    @Test
    void shouldFindAll() {
        List<Category> categories = List.of(
                new Category(1, "Miniatures"),
                new Category(2, "Euro"));

        when(repository.findAll()).thenReturn(categories);

        assertEquals(categories, service.findAll());
    }
}