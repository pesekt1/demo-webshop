package swc3.demowebshop.jdbc.DAO;

import org.springframework.jdbc.core.RowMapper;
import swc3.demowebshop.jdbc.POJO.TutorialPOJO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TutorialRowMapper implements RowMapper<TutorialPOJO> {

    @Override
    public TutorialPOJO mapRow(ResultSet resultSet, int i) throws SQLException {
        TutorialPOJO tutorial = new TutorialPOJO();
        tutorial.setId(resultSet.getInt("id"));
        tutorial.setTitle(resultSet.getString("title"));
        tutorial.setDescription(resultSet.getString("description"));
        tutorial.setPublished(resultSet.getBoolean("published"));
        return tutorial;
    }
}