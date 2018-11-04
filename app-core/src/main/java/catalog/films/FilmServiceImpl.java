package catalog.films;

import catalog.CatalogRuntimeException;
import catalog.statistics.Rating;
import catalog.statistics.View;
import catalog.users.User;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FilmServiceImpl implements FilmService {


    private final FilmRepo filmRepo;

    @Autowired
    public FilmServiceImpl(FilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }

    @Override
    public List<Film> loadAll() {
        return filmRepo.loadAll();
    }


    public Film saveOrUpdate(Film film){
        Film filmToReturn;
        Integer film_id = film.getId();
        if(film_id==null){//compare with null
            filmToReturn = save(film);
        }else {
            filmToReturn = update(film);
        }
        return filmToReturn;
    }

    @Override
    public Film save(Film film) {
        if (film == null) {
            throw new CatalogRuntimeException("Please provide film for update");
        }

        if (findByName(film.getName()).isPresent()) {
            throw new CatalogRuntimeException(String.format("Film with name %s is already exists", film.getName()));
        }

        return filmRepo.save(film);
    }

    @Override
    public Film update(Film film) {
         return filmRepo.update(film);
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
    public Optional<Film> findByName(String name) {
        Preconditions.checkNotNull(name, "Please provide film name.");
        return filmRepo.findFilmByName(name);
    }

    @Override
    public Film findById(int id) {
        return filmRepo.findFilmById(id);
    }

    @Override
    public View saveView(View view) {
        return filmRepo.saveView(view);
    }

    @Override
    public List<View> loadViewsByUser(User user) {
        return filmRepo.loadViewsByUser(user);
    }

    @Override
    public List<View> loadViewsByUserRatingFilter(User user, Rating from, Rating to) {
        return filmRepo.loadViewsByUserRatingFilter(user,from,to);
    }

    @Override
    public List<View> loadViewsByFilm(Film film) {
        return filmRepo.loadViewsByFilm(film);
    }
}
