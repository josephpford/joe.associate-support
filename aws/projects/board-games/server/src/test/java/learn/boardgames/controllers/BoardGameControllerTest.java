package learn.boardgames.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.boardgames.data.BoardGameRepository;
import learn.boardgames.models.AppUser;
import learn.boardgames.models.BoardGame;
import learn.boardgames.security.JwtConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BoardGameControllerTest {

    @MockBean
    BoardGameRepository repository;

    @Autowired
    MockMvc mvc;

    @Autowired
    JwtConverter jwtConverter;

    String token;

    @BeforeEach
    void setup() {
        AppUser appUser = new AppUser(1, "john@smith.com", "hashed-password", true, List.of("ADMIN"));
        token = jwtConverter.getTokenFromUser(appUser);
    }

    @Test
    void shouldFindAll() throws Exception {

        List<BoardGame> games = List.of(
                new BoardGame(1, "Omicron Protocol", "https://images.squarespace-cdn.com/content/v1/5ac836cf5cfd7949f0670e85/1606609211466-7HN18H67Z4GCQNNFLRV3/04_CoreMinis.png?format=500w", 3.0, 1, 4, true),
                new BoardGame(2, "A Game of Thrones: The Board Game", "https://images.squarespace-cdn.com/content/v1/5ac836cf5cfd7949f0670e85/1606609211466-7HN18H67Z4GCQNNFLRV3/04_CoreMinis.png?format=500w", 3.72, 3, 6, false),
                new BoardGame(3, "Risk", "https://images.squarespace-cdn.com/content/v1/5ac836cf5cfd7949f0670e85/1606609211466-7HN18H67Z4GCQNNFLRV3/04_CoreMinis.png?format=500w", 2.08, 2, 6, true)
        );

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(games);

        when(repository.findAll()).thenReturn(games);


        var request = get("/api/board-game");

        var response = mvc.perform(request);

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindById() throws Exception {
        BoardGame game = new BoardGame(1, "Omicron Protocol", "https://images.squarespace-cdn.com/content/v1/5ac836cf5cfd7949f0670e85/1606609211466-7HN18H67Z4GCQNNFLRV3/04_CoreMinis.png?format=500w", 3.0, 1, 4, true);

        when(repository.findById(anyInt())).thenReturn(game);

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(game);

        var request = get("/api/board-game/1");

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotFindMissingByIdAndReturn404() throws Exception {
        when(repository.findById(anyInt())).thenReturn(null);

        mvc.perform(get("/api/board-game/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAddGame() throws Exception {
        BoardGame gameIn = new BoardGame(0, "Great Space Race", "fake", 1.5, 3, 6, true);
        BoardGame gameOut = new BoardGame(1, "Great Space Race", "fake",  1.5, 3, 6, true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonIn = mapper.writeValueAsString(gameIn);
        String expectedJson = mapper.writeValueAsString(gameOut);

        when(repository.add(any())).thenReturn(gameOut);

        var request = post("/api/board-game")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddInvalidGameAndReturnHTTP400() throws Exception {
        BoardGame gameIn = new BoardGame(2, "Great Space Race", "fake",  1.5, 3, 6, false);

        ObjectMapper mapper = new ObjectMapper();
        String jsonIn = mapper.writeValueAsString(gameIn);

        var request = post("/api/board-game")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldUpdateBoardGame() throws Exception {
        // PUT
        // Content-Type: application/json
        // Body: BoardGame
        BoardGame gameIn = new BoardGame(1, "Great Space Race", "fake", 1.5, 3, 6, true);

        when(repository.update(any())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonIn = mapper.writeValueAsString(gameIn);

        var request = put("/api/board-game/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateAndReturn409WhenIdsDontMatch() throws Exception {
        BoardGame gameIn = new BoardGame(2, "Great Space Race", "fake",  1.5, 3, 6, true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonIn = mapper.writeValueAsString(gameIn);

        var request = put("/api/board-game/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void shouldNotUpdateAndReturn400WhenInvalidGame() throws Exception {
        BoardGame gameIn = new BoardGame(1, null, "fake",  1.5, 3, 6, true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonIn = mapper.writeValueAsString(gameIn);

        var request = put("/api/board-game/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotUpdateAndReturn404WhenGameNotExists() throws Exception {
        BoardGame gameIn = new BoardGame(99, "Great Space Race", "fake", 1.5, 3, 6, true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonIn = mapper.writeValueAsString(gameIn);

        when(repository.update(any())).thenReturn(false);

        var request = put("/api/board-game/99")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteGame() throws Exception {
        when(repository.deleteById(anyInt())).thenReturn(true);

        var request = delete("/api/board-game/2")
                .header("Authorization", "Bearer " + token);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingAndReturn404() throws Exception {
        when(repository.deleteById(anyInt())).thenReturn(false);

        var request = delete("/api/board-game/2")
                .header("Authorization", "Bearer " + token);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }
}