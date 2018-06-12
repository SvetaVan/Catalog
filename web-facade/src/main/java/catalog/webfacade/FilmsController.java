package catalog.webfacade;


import catalog.films.Film;
import catalog.films.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/save")
    public void save(@RequestParam("name") String name,
                     @RequestParam("genre") String genre,
                     @RequestParam("description") String description,
                     @RequestParam("picture_url") String picture_url
                     ) {
        filmService.save(new Film(name, genre, description,picture_url));
    }

    @GetMapping("/deleteAll")
    public void deleteAll() {
        filmService.deleteAll();
    }

    @GetMapping("/delete")
    public void delete(@RequestParam("id") int id){
        filmService.delete(id);
    }
}
