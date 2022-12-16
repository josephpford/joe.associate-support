package learn.boardgames.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.boardgames.data.CategoryRepository;
import learn.boardgames.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {


    @MockBean
    CategoryRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void findAll() throws Exception {

        List<Category> categories = List.of(
                new Category(1, "Miniatures"),
                new Category(2, "Euro"));

        when(repository.findAll()).thenReturn(categories);

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(categories);

        mvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }
}