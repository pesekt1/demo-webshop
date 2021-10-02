package swc3.demowebshop.jdbc.DAO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import swc3.demowebshop.jdbc.POJO.TutorialPOJO;

import java.util.List;
import java.util.Optional;

@Component
public class TutorialDAO implements DaoInterface<TutorialPOJO, Long> {

    private static final Logger log = LoggerFactory.getLogger(TutorialDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TutorialDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TutorialPOJO> findAll() {
        String sql = "SELECT * from tutorials";
        return jdbcTemplate.query(sql, new TutorialRowMapper());
    }

    @Override
    public void create(TutorialPOJO tutorial) {
        String sql = "insert into tutorials(title,description,published) values(?,?,?)";
        int insert = jdbcTemplate.update(sql,tutorial.getTitle(),tutorial.getDescription(),tutorial.getPublished());
        if(insert == 1) {
            log.info("New tutorial created: " + tutorial.getTitle());
        }
    }

    @Override
    public Optional<TutorialPOJO> findById(Long id) throws DataAccessException {
        String sql = "SELECT * from tutorials where id = ?";
        TutorialPOJO tutorial = null;
        try {
            tutorial = jdbcTemplate.queryForObject(sql, new Object[]{id}, new TutorialRowMapper());
        }catch (DataAccessException ex) {
            log.info(String.format("Tutorial not found: %s", "id"));
        }
        return Optional.ofNullable(tutorial);
    }

    @Override
    public void update(TutorialPOJO tutorial, Long id) {
        String sql = "update tutorials set title = ?, description = ?, published = ? where id = ?";
        int update = jdbcTemplate.update(sql,tutorial.getTitle(),tutorial.getDescription(),tutorial.getPublished(),id);
        if(update == 1) {
            log.info("Tutorial Updated: " + tutorial.getTitle());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from tutorials where id = ?";
        int delete = jdbcTemplate.update(sql,id);
        if(delete == 1) {
            log.info( "Tutorial Deleted: {}", id);
        }
    }


    @Override
    public List<TutorialPOJO> findAllVulnerable(String filter) {
        String sql = "SELECT * from tutorials WHERE title =" + filter;
        return jdbcTemplate.query(sql, new TutorialRowMapper());
    }
}