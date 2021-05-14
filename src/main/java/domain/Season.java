package domain;

import java.io.Serializable;

public class Season implements Serializable {
    private int id;
    private int seasonNumber;

    public Season(int id, int seasonNumber) {
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
}
