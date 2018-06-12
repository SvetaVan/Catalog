package catalog.users;

import catalog.CatalogRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

@Component
public class UserRepoImpl implements UsersRepo {

    private static final RowMapper<User> USER_ROW_MAPPER = (ResultSet rs, int rowNum) -> new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("password")
    );

    private static final String SAVE_QUERY = "insert into users (name, password) values (:name, :password)";
    private static final String LOAD_ALL = "select id, name, password from users";
    private static final String DELETE_ALL = "delete from users";
    private static final String DELETE_BY_ID = "delete from users where id = :id";
    private static final String FIND_BY_NAME = "select id, name, password from users where name = :name";
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepoImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public List<User> loadAll() {
        return jdbcTemplate.query(LOAD_ALL,
                USER_ROW_MAPPER
        );
    }

    @Override
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                SAVE_QUERY,
                new MapSqlParameterSource()
                        .addValue("name", user.getName())
                        .addValue("password", user.getPassword()),
                keyHolder
        );
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new CatalogRuntimeException("The key wasn't generated, insert failed on user.name: " + user.getName());
        }
        user.setId(key.intValue());
        return user;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_BY_ID,
                new MapSqlParameterSource()
                        .addValue("id", id)
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL,
                new MapSqlParameterSource()
        );
    }

    @Override
    public User findByName(String name) {
        return jdbcTemplate.queryForObject(FIND_BY_NAME,
                new MapSqlParameterSource()
                        .addValue("name", name),
                USER_ROW_MAPPER
        );
    }
}
