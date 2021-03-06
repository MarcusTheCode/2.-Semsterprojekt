package domain;

import data.DataFacade;
import java.io.Serializable;

public class CastMember implements Serializable {

    private int productionID;
    private String jobTitle;
    private int artistID;
    private Artist artist;
    private String name;
    private String email;

    public CastMember(int productionID, String jobTitle, int artistID) {
        this.productionID = productionID;
        this.jobTitle = jobTitle;
        this.artistID = artistID;
        this.artist = getArtistFormDatabase();
    }

    // TODO: change getArtist(name) to getArtist(email) after implementation of email in production.fxml
    public CastMember(String name, String email, String jobTitle, int productionID) {
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.productionID = productionID;
        this.artist = DataFacade.getArtist(email); //if null artist doesn't exists
        if (this.artist != null)
            this.artistID = artist.getId();
    }

    public int getProductionID() {
        return productionID;
    }

    public int getId() {
        return productionID;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    private Artist getArtistFormDatabase(){
        return DataFacade.getArtist(artistID);
    }

    public Artist getArtist() {
        return artist;
    }

    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name){
        artist.setName(name);
    }

    public void setProductionID(int productionID) {
        this.productionID = productionID;
    }

    public boolean equals(CastMember castMember) {
        if (this.getName().equals(castMember.getName())
                && this.getEmail().equals(castMember.getEmail())
                && this.getJobTitle().equals(castMember.getJobTitle())) {
            return true;
        }
        return false;
    }
}
