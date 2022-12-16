package learn.boardgames.domain;

import learn.boardgames.data.BoardGameRepository;
import learn.boardgames.data.CategoryRepository;
import learn.boardgames.models.BoardGame;
import learn.boardgames.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardGameService {
    private final BoardGameRepository repository;
    private final CategoryRepository categoryRepository;

    public BoardGameService(BoardGameRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public List<BoardGame> findAll() {
        return repository.findAll();
    }

    public List<BoardGame> findByNumberOfPlayers(int numberOfPlayers) {
        return repository.findByNumberOfPlayers(numberOfPlayers);
    }

    public List<BoardGame> findByName(String name) {
        return repository.findByName(name);
    }

    public BoardGame findById(int id) {
        return repository.findById(id);
    }

    public Result<BoardGame> add(BoardGame game) {
        Result<BoardGame> result = validate(game);
        if (!result.isSuccess()) {
            return result;
        }
        if (game.getId() > 0) {
            result.addMessage("'id' should not be set for 'add'.", ResultType.INVALID);
        }

        if (result.isSuccess()) {
            game = repository.add(game);
            result.setPayload(game);
        }

        return result;
    }

    public Result<BoardGame> update(BoardGame game) {
        Result<BoardGame> result = validate(game);
        if (!result.isSuccess()) {
            return result;
        }
        if (game.getId() < 1) {
            result.addMessage("'id' should be set for 'update'.", ResultType.INVALID);
        }

        if (result.isSuccess() && !repository.update(game)) {
            result.addMessage(notFoundMessage(game.getId()), ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<BoardGame> validate(BoardGame game) {
        Result<BoardGame> result = new Result<>();

        if (game == null) {
            result.addMessage("'game' must not be null.", ResultType.INVALID);
            return result;
        }

        if (game.getName() == null || game.getName().isBlank()) {
            result.addMessage("'name' is required.", ResultType.INVALID);
        }

        if (game.getRating() < 1 || game.getRating() > 10) {
            result.addMessage("'rating' must be between 1 and 10.", ResultType.INVALID);
        }

        // TODO: add unit test coverage
        // TODO: add url format validation
        if (game.getImageUrl() == null || game.getImageUrl().isBlank()) {
            result.addMessage("'imageUrl' is required.", ResultType.INVALID);
        }

        if (game.getMinimumPlayers() < 1 || game.getMinimumPlayers() > 75) {
            result.addMessage("'minimumPlayers' must be between 0 and 76.", ResultType.INVALID);
        }

        if (game.getMaximumPlayers() < 1 || game.getMaximumPlayers() > 75) {
            result.addMessage("'maximumPlayers' must be between 0 and 76.", ResultType.INVALID);
        }

        if (game.getMinimumPlayers() > game.getMaximumPlayers()) {
            result.addMessage("'minimumPlayers' must not be greater than 'maximumPlayers'.", ResultType.INVALID);
        }

        if (isDuplicate(game)) {
            result.addMessage("Duplicate games are not allowed.", ResultType.INVALID);
        }

        if (!result.isSuccess()) {
            return result;
        }

        List<Category> categories = categoryRepository.findAll();
        for (Category category : game.getCategories()) {
            if (categories.stream().noneMatch(c -> c.getId() == category.getId())) {
                String message = String.format("category '%s' does not exist.", category.getName());
                result.addMessage(message, ResultType.INVALID);
            }
        }

        return result;
    }

    private boolean isDuplicate(BoardGame game) {
        List<BoardGame> games = repository.findAll();
        for (BoardGame g : games) {
            if (g.getId() != game.getId() && g.getName().equals(game.getName())) {
                return true;
            }
        }

        return false;
    }

    public Result<BoardGame> deleteById(int id) {
        Result<BoardGame> result = new Result<>();
        if (!repository.deleteById(id)) {
            result.addMessage(notFoundMessage(id), ResultType.NOT_FOUND);
        }
        return result;
    }

    private String notFoundMessage(int id) {
        return String.format("Board game with id: '%s' was not found.", id);
    }
}
