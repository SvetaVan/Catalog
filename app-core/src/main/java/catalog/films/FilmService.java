package catalog.films;

import catalog.users.User;

import java.util.List;

public interface FilmService {

    List<Film> loadAll();

    Film save(Film film);

    void delete(int id);

    void deleteAll();

    Film findByName(String name);
}
