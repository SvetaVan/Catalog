package catalog.films;

import catalog.CatalogRuntimeException;
import catalog.users.User;
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
public class FilmRepoImpl implements FilmRepo {

    private static final RowMapper<Film> FILM_ROW_MAPPER = (ResultSet rs, int rowNum) -> new Film(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("genre"),
            rs.getString("description"),
            rs.getString("picture_url")//???correct name
    );


    private static final String SAVE_QUERY = "insert into films (name, genre, description, picture_url) values (:name, :genre, :description, :picture_url)";
    private static final String LOAD_ALL = "select * from films";
    private static final String DELETE_ALL = "delete from films";
    private static final String DELETE_BY_ID = "delete from films where id = :id";
    private static final String FIND_BY_NAME = "select * description from films where name = :name";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public FilmRepoImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Film save(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                SAVE_QUERY,
                new MapSqlParameterSource()
                        .addValue("name", film.getName())
                        .addValue("genre", film.getGenre())
                        .addValue("description", film.getDecription())
                        .addValue("picture_url", film.getPictureURL()),
                keyHolder
        );
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new CatalogRuntimeException("The key wasn't generated, insert failed on film.name: " + film.getName());
        }
        film.setId(key.intValue());
        return film;
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
    public List<Film> loadAll() {
        return jdbcTemplate.query(LOAD_ALL,
                FILM_ROW_MAPPER
        );
    }

    @Override
    public Film findByName(String name) {
        return jdbcTemplate.queryForObject(FIND_BY_NAME,
                new MapSqlParameterSource()
                        .addValue("name", name),
                FILM_ROW_MAPPER
        );
    }
}
