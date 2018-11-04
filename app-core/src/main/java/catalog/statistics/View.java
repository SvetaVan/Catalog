package catalog.statistics;

import catalog.films.Film;

public class View {
    private int id;
    private int user_id;
    private Film film;
    private Rating rating;
    private String comment;

    public View(int id, int user_id, Film film, Rating rating, String comment) {
        this.id = id;
        this.user_id = user_id;
        this.film = film;
        this.rating = rating;
        this.comment = comment;
    }

    public View(int user_id, Film film, Rating rating, String comment) {
        this.user_id = user_id;
        this.film = film;
        this.rating = rating;
        this.comment = comment;
    }

    public View() {
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return user_id;
    }

    public Film getFilmId() {
        return film;
    }

    public Rating getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setId(int id) {
        this.id = id;
    }
}
