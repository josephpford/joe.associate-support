package learn.boardgames.data;

import learn.boardgames.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryJdbcTemplateRepository implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        final String sql = "select category_id, `name` from category;";
        return jdbcTemplate.query(sql, new CategoryMapper());
    }
}
