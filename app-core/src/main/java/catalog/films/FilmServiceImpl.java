package catalog.films;

import catalog.CatalogRuntimeException;
import catalog.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmServiceImpl implements FilmService {


    @Autowired
    private FilmRepo filmRepo;


    @Override

    public List<Film> loadAll() {
        return filmRepo.loadAll();
    }

    @Override
    public Film save(Film film) {
        if(filmRepo.loadAll().contains(film)){
            throw new CatalogRuntimeException(String.format("Film with name %s is already exists.", film.getName()));
        }
        return filmRepo.save(film);
    }

    @Override
    public void delete(int id) {
        filmRepo.delete(id);
    }

    @Override
    public void deleteAll() {
        filmRepo.deleteAll();
    }

    @Override
    public Film findByName(String name) {
        return filmRepo.findByName(name);
    }
}
