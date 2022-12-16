package learn.boardgames.data;

import learn.boardgames.models.BoardGame;
import learn.boardgames.models.WeightCategory;

import java.util.List;
import java.util.Map;

public interface BoardGameRepository {

    List<BoardGame> findAll();

    BoardGame findById(int id);

    List<BoardGame> findByNumberOfPlayers(int numberOfPlayers);

    List<BoardGame> findByName(String name);

    BoardGame add(BoardGame game);

    boolean update(BoardGame game);

    boolean deleteById(int id);
}
