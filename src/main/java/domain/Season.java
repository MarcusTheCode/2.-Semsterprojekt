package domain;

import java.io.Serializable;

public class Season implements Serializable {
    private int id;
    private int seasonNumber;
    private int seriesID;

    public Season(int seasonNumber, int seriesID) {
        this.seasonNumber = seasonNumber;
        this.seriesID = seriesID;
    }

    public Season(int id, int seasonNumber, int seriesID) {
        this.id = id;
        this.seasonNumber = seasonNumber;
        this.seriesID = seriesID;
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

    public int getSeriesID() {
        return seriesID;
    }

    public void setSeriesID(int seriesID) {
        this.seriesID = seriesID;
    }
}
