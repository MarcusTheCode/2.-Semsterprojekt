package domain;

import java.io.Serializable;

public class Genre implements Serializable {
    private int id;
    private String genre;

    public Genre(String genre) {
        this.genre = genre;
    }

    public Genre(int id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return genre;
    }
}
