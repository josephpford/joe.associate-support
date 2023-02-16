package learn.boardgames.data;

import learn.boardgames.models.BoardGame;
import learn.boardgames.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BoardGameJdbcTemplateRepositoryTest {

    @Autowired
    BoardGameJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    static boolean hasSetup = false;

    @BeforeEach
    void setup() {
        if (!hasSetup) {
            hasSetup = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
    }

    /*
    Tetsing strategy:
    1 - leave alone
    2 - update
    3 - delete
     */

    @Test
    void shouldFindAll() {

        List<BoardGame> actual = repository.findAll();

        assertTrue(actual.size() >= 2);
        assertTrue(actual.stream().anyMatch(g -> g.getName().equals("Omicron Protocol")));
    }

    @Test
    void shouldFindById() {
        BoardGame game = repository.findById(1);

        assertEquals(1, game.getId());
        assertEquals("Omicron Protocol", game.getName());
        assertEquals(7.0, game.getRating());
        assertEquals(1, game.getMinimumPlayers());
        assertEquals(4, game.getMaximumPlayers());
        assertEquals(3, game.getCategories().size());
        assertTrue(game.getCategories().stream().anyMatch(c -> c.getName().equals("Miniatures")));
        assertTrue(game.isInPrint());
    }

    @Test
    void shouldFindByName() {
        List<BoardGame> actual = repository.findByName("Omicron Protocol");
        assertEquals(1, actual.size());
        assertTrue(actual.stream().anyMatch(g -> g.getName().equals("Omicron Protocol")));
    }

    @Test
    void shouldFindByNumberOfPlayers() {
        List<BoardGame> actual = repository.findByNumberOfPlayers(3);
        assertTrue(actual.size() >= 2);
        assertTrue(actual.stream().anyMatch(g -> g.getName().equals("Omicron Protocol")));
    }

    @Test
    void shouldAddGame() {
        BoardGame game = new BoardGame();
        game.setName("Wingspan");
        game.setImageUrl("https://cf.geekdo-images.com/yLZJCVLlIx4c7eJEWUNJ7w__itemrep/img/DR7181wU4sHT6gn6Q1XccpPxNHg=/fit-in/246x300/filters:strip_icc()/pic4458123.jpg");
        game.setRating(2.5);
        game.setMinimumPlayers(1);
        game.setMaximumPlayers(5);
        game.setInPrint(true);

        Category category = new Category(2, "Euro");

        game.setCategories(List.of(category));

        BoardGame inserted = repository.add(game);

        assertEquals(4, inserted.getId());

        BoardGame actual = repository.findById(4);

        assertNotNull(actual);
        assertEquals(inserted, actual);
        assertTrue(game.getCategories().stream().anyMatch(c -> c.getName().equals("Euro")));

    }

    @Test
    void shouldUpdateBoardGame() {
        BoardGame game = new BoardGame();
        game.setId(2);
        game.setName("Updated Game");
        game.setImageUrl("fake");
        game.setRating(3.33);
        game.setMinimumPlayers(6);
        game.setMaximumPlayers(66);
        game.setInPrint(false);

        Category category = new Category(2, "Euro");

        game.setCategories(List.of(category));

        assertTrue(repository.update(game));

        BoardGame actual = repository.findById(2);

        assertEquals(game, actual);
        assertTrue(game.getCategories().stream().anyMatch(c -> c.getName().equals("Euro")));
    }

    @Test
    void shouldNotUpdateMissing() {
        BoardGame game = new BoardGame();
        game.setId(99);
        game.setName("Non-existent Game");

        assertFalse(repository.update(game));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }
}