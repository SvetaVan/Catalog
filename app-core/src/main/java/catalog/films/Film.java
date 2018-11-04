package catalog.films;

import java.sql.ResultSet;
import java.util.Objects;

public class Film {
    private int id;
    private String name;
    private String genre; // lets make enum
    private String description;
    private String pictureURL;

    public Film (ResultSet resultSet, int number){

    }

    public Film(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Film(String name, String genre, String description, String pictureURL) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.pictureURL = pictureURL;
    }

    public Film(int id, String name, String genre, String description, String pictureURL) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.pictureURL = pictureURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id &&
                Objects.equals(name, film.name) &&
                Objects.equals(genre, film.genre) &&
                Objects.equals(description, film.description) &&
                Objects.equals(pictureURL, film.pictureURL);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, genre, description, pictureURL);
    }


    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                '}';
    }
}
