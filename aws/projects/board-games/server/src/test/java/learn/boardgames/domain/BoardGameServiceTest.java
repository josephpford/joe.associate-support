package learn.boardgames.domain;

import learn.boardgames.data.BoardGameRepository;
import learn.boardgames.data.CategoryRepository;
import learn.boardgames.models.BoardGame;
import learn.boardgames.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BoardGameServiceTest {

    @MockBean
    BoardGameRepository repository;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    BoardGameService service;

    @Test
    void shouldFindAll() {
        List<BoardGame> games = List.of(
                new BoardGame(1, "Omicron Protocol", "fake",  3.0, 1, 4, true),
                new BoardGame(2, "A Game of Thrones: The Board Game", "fake",  3.72, 3, 6, false),
                new BoardGame(3, "Risk", "fake", 2.08, 2, 6, true)
        );

        when(repository.findAll()).thenReturn(games);

        List<BoardGame> actual = service.findAll();

        assertEquals(3, actual.size());
        assertEquals("Risk", actual.get(2).getName());
    }

    @Test
    void shouldFindById() {

        var game = new BoardGame(99, "A", "fake", 2.08, 2, 6, true);

        when(repository.findById(anyInt())).thenReturn(game);

        BoardGame actual = service.findById(99);

        assertNotNull(actual);
        assertEquals(99, actual.getId());
        assertEquals("A", actual.getName());
    }

    @Test
    void shouldFindByNumberOfPlayers() {
        var game = new BoardGame(99, "A", "fake", 2.08, 1, 6, true);

        when(repository.findByNumberOfPlayers(anyInt())).thenReturn(List.of(game));

        List<BoardGame> actual = service.findByNumberOfPlayers(1);

        assertEquals(1, actual.size());
    }

    @Test
    void shouldFindByName() {
        var game = new BoardGame(99, "A", "fake",  2.08, 1, 6, false);

        when(repository.findByName(any())).thenReturn(List.of(game));

        List<BoardGame> actual = service.findByName(null);

        assertEquals(1, actual.size());
    }

    @Test
    void shouldAddBoardGame() {
        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 2.5, 2, 5, false);

        Category category = new Category(2, "Euro");
        game.setCategories(List.of(category));

        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(repository.add(any())).thenReturn(game);

        Result actual = service.add(game);

        assertTrue(actual.isSuccess());
        assertNotNull(actual.getPayload());
    }

    @Test
    void shouldNotAddNullBoardGame() {
        BoardGame game = null;

        Result actual = service.add(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
    }

    @Test
    void shouldNotAddWithExistingId() {
        BoardGame game = new BoardGame(1, "Settlers of Catan", "fake", 2.5, 2, 5, true);

        Result actual = service.add(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(1, actual.getMessages().size());
        assertEquals("'id' should not be set for 'add'.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithNullName() {
        BoardGame game = new BoardGame(0, null, "fake", 2.5, 2, 5, true);

        Result actual = service.add(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(1, actual.getMessages().size());
        assertEquals("'name' is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithBlankName() {
        BoardGame game = new BoardGame(0, "  ", "fake", 2.5, 2, 5, true);

        Result actual = service.add(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(1, actual.getMessages().size());
        assertEquals("'name' is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithZeroRating() {
        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 0, 2, 5, true);

        Result actual = service.add(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(1, actual.getMessages().size());
        assertEquals("'rating' must be between 1 and 10.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWith11Rating() {
        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 11, 2, 5, true);

        Result actual = service.add(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(1, actual.getMessages().size());
        assertEquals("'rating' must be between 1 and 10.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithZeroMinimumPlayers() {
        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 2.5, 0, 5, false);

        Result actual = service.add(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(1, actual.getMessages().size());
        assertEquals("'minimumPlayers' must be between 0 and 76.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWith76MinimumPlayers() {
        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 2.5, 76, 75, true);

        Result actual = service.add(game);

        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(2, actual.getMessages().size());
        assertEquals("'minimumPlayers' must be between 0 and 76.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithZeroMaximumPlayers() {
        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 2.5, 1, 0, false);

        Result actual = service.add(game);

        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(2, actual.getMessages().size());
        assertEquals("'maximumPlayers' must be between 0 and 76.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWith76MaximumPlayers() {
        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 2.5, 76, 76, false);

        Result actual = service.add(game);

        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(2, actual.getMessages().size());
        assertEquals("'maximumPlayers' must be between 0 and 76.", actual.getMessages().get(1));
    }

    @Test
    void shouldNotAddMinPlayersGreaterThanMax() {

        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 2.5, 2, 1, false);

        Result actual = service.add(game);

        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(1, actual.getMessages().size());
        assertEquals("'minimumPlayers' must not be greater than 'maximumPlayers'.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddDuplicateGame() {
        BoardGame game = new BoardGame(0, "Omicron Protocol", "fake", 3.0, 1, 4, true);

        List<BoardGame> games = List.of(
                new BoardGame(1, "Omicron Protocol", "fake", 3.0, 1, 4, true),
                new BoardGame(2, "A Game of Thrones: The Board Game", "fake", 3.72, 3, 6, false),
                new BoardGame(3, "Risk", "fake", 2.08, 2, 6, true)
        );

        when(repository.findAll()).thenReturn(games);

        Result actual = service.add(game);

        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Duplicate games are not allowed.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithNotExistingCategory() {

        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 2.5, 1, 4, false);

        Category category = new Category(2, "Euro");
        game.setCategories(List.of(category));

        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        Result actual = service.add(game);

        assertFalse(actual.isSuccess());
        assertNull(actual.getPayload());
        assertEquals(1, actual.getMessages().size());
        assertEquals("category 'Euro' does not exist.", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdateGame() {
        BoardGame game = new BoardGame(99, "Settlers of Catan", "fake", 2.5, 2, 5, true);

        when(repository.update(any())).thenReturn(true);

        Result actual = service.update(game);

        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNullBoardGame() {
        BoardGame game = null;

        Result actual = service.update(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateNotExisting() {
        BoardGame game = new BoardGame(1, "Settlers of Catan", "fake", 2.5, 2, 5, true);

        Result actual = service.update(game);

        assertEquals(ResultType.NOT_FOUND, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Board game with id: '1' was not found.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithNoId() {
        BoardGame game = new BoardGame(0, "Settlers of Catan", "fake", 2.5, 2, 5, true);

        Result actual = service.update(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertEquals("'id' should be set for 'update'.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateToDuplicate() {
        BoardGame game = new BoardGame(1, "Omicron Protocol", "fake", 3.0, 1, 4, true);

        List<BoardGame> games = List.of(
                new BoardGame(1, "Omicron Protocol", "fake", 3.0, 1, 4, true),
                new BoardGame(2, "A Game of Thrones: The Board Game", "fake", 3.72, 3, 6, false),
                new BoardGame(3, "Risk", "fake",  2.08, 2, 6, true)
        );

        when(repository.findAll()).thenReturn(games);

        game.setName("A Game of Thrones: The Board Game");

        Result actual = service.update(game);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Duplicate games are not allowed.", actual.getMessages().get(0));
    }

    @Test
    void shouldDeleteBoardGame() {
        when(repository.deleteById(anyInt())).thenReturn(true);
        Result actual = service.deleteById(99);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteNotExisting() {
        Result actual = service.deleteById(1);

        assertEquals(ResultType.NOT_FOUND, actual.getResultType());
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Board game with id: '1' was not found.", actual.getMessages().get(0));
    }
}