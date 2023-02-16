package learn.boardgames.data;

import learn.boardgames.models.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

}
