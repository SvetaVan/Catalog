package catalog.films;

import catalog.statistics.Rating;
import catalog.statistics.View;
import catalog.users.User;

import java.util.List;
import java.util.Optional;

public interface FilmRepo {

    Film save(Film film);//

    void delete(int id);

    void deleteAll();

    List<Film> loadAll();

    Film update(Film film);//

    Optional<Film> findFilmByName(String name);

    Film findFilmById(int id);

    View saveView(View view);

    List<View> loadViewsByUser(User user);

    List<View> loadViewsByUserRatingFilter(User user, Rating from, Rating to);

    List<View>  loadViewsByFilm (Film film);

}
