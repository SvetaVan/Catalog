package catalog.webfacade;


import catalog.films.Film;
import catalog.films.FilmService;
import catalog.statistics.Rating;
import catalog.statistics.View;
import catalog.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
public class FilmsController {


    private FilmService filmService;

    @Autowired
    public FilmsController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping(value = "/load")
    @ResponseBody
    public List<Film> loadAllFilms() {
        return filmService.loadAll();
    }

    //не работал с postMapping
    @GetMapping(value = "/save")
    public ServiceResult save(@RequestParam("name") String name,
                              @RequestParam("genre") String genre,
                              @RequestParam("description") String description,
                              @RequestParam("picture_url") String picture_url
    ) {
        return ServiceResult.guardedInvoke(()->
                filmService.save(
                        new Film(name, genre, description, picture_url)
                )
        );
    }

    @GetMapping(value = "/deleteAll")
    public void deleteAll() {
        filmService.deleteAll();
    }

    @GetMapping(value = "/delete")
    public void delete(@RequestParam("id") int id) {
        filmService.delete(id);
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public Optional<Film> findByName(@RequestParam("name") String name) {
        return filmService.findByName(name);
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public Film update(@RequestParam("id") int id,
                       @RequestParam("name") String name,
                       @RequestParam("genre") String genre,
                       @RequestParam("description") String description,
                       @RequestParam("picture_url") String picture_url
    ) {
        return filmService.update(new Film(id, name, genre, description, picture_url));
    }


    //do we need to save film, maybe id is enough?
    @PostMapping(value = "/saveView")
    public void save(@RequestParam("film_id") int film_id,
                     @RequestParam("user_id") int user_id,
                     @RequestParam("rating") String rating,
                     @RequestParam("comment") String comment
    ) {
     //   filmService.saveView(new View(user_id, film_id, Rating.valueOf(rating),comment));
    }

    @GetMapping(value = "/loadViewsByUser")
    @ResponseBody
    public List<View> loadViewsByUser(@RequestParam("id") int id,
                                      @RequestParam("name") String name,
                                      @RequestParam("password") String password
                                      ){
        return filmService.loadViewsByUser(new User(id, name,password));
    }


    @GetMapping(value = "/loadViewsByFilm")
    @ResponseBody
    public List<View> loadViewsByFilm(@RequestParam("id") int id,
                                      @RequestParam("name") String name
                                      ){
        return filmService.loadViewsByFilm(new Film(id, name));
    }

    @GetMapping(value = "/loadViewsByUserRatingFilter")
    @ResponseBody
    public List<View> loadViewsByUser(@RequestParam("id") int id,
                                      @RequestParam("name") String name,
                                      @RequestParam("password") String password,
                                      @RequestParam("from") int from,
                                      @RequestParam("to") int to
    ){
        return filmService.loadViewsByUserRatingFilter(new User(id, name,password),Rating.getRating(from),Rating.getRating(to));
    }









}
