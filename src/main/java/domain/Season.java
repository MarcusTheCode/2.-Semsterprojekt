package domain;

import java.io.Serializable;

public class Season implements Serializable {
    private int id;
    private int seasonNumber;
    private String name;

    public Season(String name) {
        this.name = name;
    }

    public Season(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public Season(int id, String name, int seasonNumber) {
        this.name = name;
        this.id = id;
        this.seasonNumber = seasonNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
