package catalog.films;

import catalog.CatalogRuntimeException;
import catalog.statistics.Rating;
import catalog.statistics.View;
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
import java.util.Optional;


@Component
public class FilmRepoImpl implements FilmRepo {

    private static final RowMapper<Film> FILM_ROW_MAPPER = (ResultSet rs, int rowNum) -> new Film(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("genre"),
            rs.getString("description"),
            rs.getString("picture_url")
    );
    private static final RowMapper<View> VIEW_ROW_MAPPER = (ResultSet rs, int rowNum) -> new View(
            rs.getInt("id"),
            rs.getInt("user_id"),
            (Film) rs.getObject("film_id"),
            (Rating)rs.getObject("rating"),
            rs.getString("comment")
    );


    private static final String SAVE_QUERY = "insert into films (name, genre, description, picture_url) values " +
            "(:name, :genre, :description, :picture_url)";
    private static final String LOAD_ALL = "select * from films";
    private static final String DELETE_ALL = "delete from films";
    private static final String DELETE_BY_ID = "delete from films where id = :id";
    private static final String FIND_BY_NAME = "select * from films where name = :name";
    private static final String FIND_BY_ID = "select * from films where id = :id";
    private static final String UPDATE = "update films set name = :name, genre = :genre, description = :description, " +
            "picture_url = :picture_url where id = :id";
    private static final String SAVE_VIEW_QUERY = "insert into film_user_xref (film_id, user_id, rating, comment) values" +
            "(:film_id, :user_id, :rating, :comment)";
    private static final String LOAD_VIEWS_BY_USER = "select * from film_user_xref where user_id=:user_id";
    private static final String LOAD_VIEWS_BY_USER_RATING_FILTER = "select * from film_user_xref where user_id=:user_id and rating between  :from and :to";
    private static final String LOAD_VIEWS_BY_FILM = "select * from film_user_xref where film_id=:film_id";

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
                        .addValue("description", film.getDescription())
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
    public Film update(Film film) {
        int numberOfRowsAffected = jdbcTemplate.update(UPDATE,
                new MapSqlParameterSource()
                        .addValue("id", film.getId())
                        .addValue("name", film.getName())
                        .addValue("genre", film.getGenre())
                        .addValue("description", film.getDescription())
                        .addValue("picture_url", film.getPictureURL())
        );
        if (numberOfRowsAffected != 0) {
            return film;
        } else {
            throw new CatalogRuntimeException("Passed film wasn't updated");
        }
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
                //FILM_ROW_MAPPER
                Film::new
        );
    }

    @Override
    public Optional<Film> findFilmByName(String name) {
        List<Film> films = jdbcTemplate.query(FIND_BY_NAME,
                new MapSqlParameterSource()
                        .addValue("name", name),
                FILM_ROW_MAPPER
        );
        if (films.size()==0) {
            return Optional.empty();
        }else {
            return Optional.of(films.get(0));
        }

    }

    @Override
    public Film findFilmById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID,
                new MapSqlParameterSource()
                        .addValue("id", id),
                FILM_ROW_MAPPER
        );
    }



    @Override
    public View saveView(View view) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                SAVE_VIEW_QUERY,
                new MapSqlParameterSource()
                        .addValue("film_id", view.getFilmId())
                        .addValue("user_id", view.getUserId())
                        .addValue("rating", view.getRating())
                        .addValue("comment", view.getComment()),
                keyHolder
        );
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new CatalogRuntimeException(String.format("The key wasn't generated, insert saveView failed on film: %s",
                    findFilmById(view.getId()).getName()));
        }
        view.setId(key.intValue());
        return view;
    }

    @Override
    public List<View> loadViewsByUser(User user) {
        return jdbcTemplate.query(LOAD_VIEWS_BY_USER,
                new MapSqlParameterSource()
                .addValue("user_id", user.getId()),
                VIEW_ROW_MAPPER
        );
    }

    @Override
    public List<View> loadViewsByUserRatingFilter(User user, Rating from, Rating to) {
        return jdbcTemplate.query(LOAD_VIEWS_BY_USER_RATING_FILTER,
                new MapSqlParameterSource()
                        .addValue("user_id", user.getId())
                        .addValue("from", from)
                        .addValue("to", to),
                VIEW_ROW_MAPPER
        );
    }

    @Override
    public List<View> loadViewsByFilm(Film film) {
        return jdbcTemplate.query(LOAD_VIEWS_BY_FILM,
                new MapSqlParameterSource()
                        .addValue("film_id", film.getId()),
                VIEW_ROW_MAPPER
        );
    }


}

