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
        if (!DataFacade.castMemberExists(this)){
            DataFacade.insertCastMember(this);
        }
    }

    // TODO: change getArtist(name) to getArtist(email) after implementation of email in production.fxml
    public CastMember(String name, String email, String jobTitle, int productionID) {
        this.name = name;
        this.email =
        this.jobTitle = jobTitle;
        this.productionID = productionID;
        /*
        this.artist = DataFacade.getArtist(name);
        if (this.artist==null){
            DataFacade.insertArtist(new Artist(name, email));
            this.artist = DataFacade.getArtist(name);
        }
        this.artistID = artist.getId();
        if (!DataFacade.castMemberExists(this)){
            DataFacade.insertCastMember(this);
        }
         */

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
        return artist.getName();
    }

    public void setName(String name){
        artist.setName(name);
    }
}
