package catalog.films;

import java.util.Objects;

public class Film {
    private int id;
    private String name;
    private String genre;
    private String decription;
    private String pictureURL;

    public Film() {
    }


    public Film(String name, String genre, String decription, String pictureURL) {
        this.name = name;
        this.genre = genre;
        this.decription = decription;
        this.pictureURL = pictureURL;
    }

    public Film(int id, String name, String genre, String decription, String pictureURL) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.decription = decription;
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

    public String getDecription() {
        return decription;
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
                Objects.equals(decription, film.decription) &&
                Objects.equals(pictureURL, film.pictureURL);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, genre, decription, pictureURL);
    }


    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", decription='" + decription + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                '}';
    }
}
