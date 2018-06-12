package catalog.films;

import java.util.List;

public interface FilmRepo {

    Film save(Film film);

    void delete(int id);

    void deleteAll();

    List<Film> loadAll();

    Film findByName(String name);

}
