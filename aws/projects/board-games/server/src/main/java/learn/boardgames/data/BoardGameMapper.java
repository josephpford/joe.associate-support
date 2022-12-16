package learn.boardgames.data;

import learn.boardgames.models.BoardGame;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardGameMapper implements RowMapper<BoardGame> {

    @Override
    public BoardGame mapRow(ResultSet resultSet, int i) throws SQLException {
        BoardGame game = new BoardGame();
        game.setId(resultSet.getInt("board_game_id"));
        game.setName(resultSet.getString("name"));
        game.setImageUrl(resultSet.getString("image_url"));
        game.setRating(resultSet.getDouble("rating"));
        game.setMinimumPlayers(resultSet.getInt("minimum_players"));
        game.setMaximumPlayers(resultSet.getInt("maximum_players"));
        game.setInPrint(resultSet.getBoolean("in_print"));
        return game;
    }
}
