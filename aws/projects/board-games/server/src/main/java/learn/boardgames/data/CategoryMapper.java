package learn.boardgames.data;

import learn.boardgames.models.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Category(
                resultSet.getInt("category_id"),
                resultSet.getString("name"));
    }
}
