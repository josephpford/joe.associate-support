package learn.boardgames.data;

import learn.boardgames.models.AppUser;
import learn.boardgames.models.BoardGame;
import learn.boardgames.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BoardGameJdbcTemplateRepository implements BoardGameRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BoardGameMapper mapper = new BoardGameMapper();

    public BoardGameJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BoardGame> findAll() {
        final String sql = "select board_game_id, `name`, image_url, rating, " +
                "minimum_players, maximum_players, in_print " +
                "from board_game;";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    @Transactional
    public BoardGame findById(int id) {
        BoardGame game = findBy("where board_game_id = ?;", id).stream()
                .findFirst()
                .orElse(null);

        if (game != null) {
            addCategories(game);
        }

        return game;
    }

    @Override
    public List<BoardGame> findByNumberOfPlayers(int numberOfPlayers) {
        return findBy("where ? between minimum_players and maximum_players;", numberOfPlayers);
    }

    @Override
    public List<BoardGame> findByName(String name) {
        return findBy("where `name` like concat('%', ?, '%');", name);
    }

    private List<BoardGame> findBy(String where, Object param) {
        final String sql = "select board_game_id, `name`, image_url, rating, " +
                "minimum_players, maximum_players, in_print " +
                "from board_game " + where;

        return jdbcTemplate.query(sql, mapper, param);
    }

    @Override
    @Transactional
    public BoardGame add(BoardGame game) {
        final String sql = "insert into board_game (`name`, image_url, rating, minimum_players, maximum_players, in_print) " +
                "values (?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, game.getName());
            ps.setString(2, game.getImageUrl());
            ps.setDouble(3, game.getRating());
            ps.setInt(4, game.getMinimumPlayers());
            ps.setInt(5, game.getMaximumPlayers());
            ps.setBoolean(6, game.isInPrint());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        game.setId(keyHolder.getKey().intValue());

        updateCategories(game);

        return game;
    }

    @Override
    @Transactional
    public boolean update(BoardGame game) {
        final String sql = """
                update board_game set 
                    `name` = ?, 
                    image_url = ?, 
                    rating = ?, 
                    minimum_players = ?, 
                    maximum_players = ?, 
                    in_print = ? 
                    where board_game_id = ?;
                """;
        boolean updated = jdbcTemplate.update(sql,
                game.getName(),
                game.getImageUrl(),
                game.getRating(),
                game.getMinimumPlayers(),
                game.getMaximumPlayers(),
                game.isInPrint(),
                game.getId()) > 0;

        if (updated) {
            updateCategories(game);
        }

        return updated;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        jdbcTemplate.update("delete from board_game_category where board_game_id = ?;", id);
        return jdbcTemplate.update("delete from board_game where board_game_id = ?;", id) > 0;
    }

    private void updateCategories(BoardGame game) {

        jdbcTemplate.update("delete from board_game_category where board_game_id = ?;", game.getId());

        for (Category category : game.getCategories()) {
            String sql = """
                    insert into board_game_category (board_game_id, category_id)
                    values (?, ?);
                    """;
            jdbcTemplate.update(sql, game.getId(), category.getId());
        }

    }

    private void addCategories(BoardGame game) {
        final String sql = """
            select
                c.category_id,
                c.name
            from category c
            inner join board_game_category gc on gc.category_id = c.category_id
            where gc.board_game_id = ?;
        """;

        List<Category> boardGameCategories = jdbcTemplate.query(sql, new CategoryMapper(), game.getId());

        game.setCategories(boardGameCategories);
    }
}
