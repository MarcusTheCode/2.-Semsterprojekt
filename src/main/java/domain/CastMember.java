package domain;

import data.DataFacade;
import java.io.Serializable;

public class CastMember implements Serializable {

    private int productionID;
    private String jobTitle;
    private int artistID;
    private Artist artist;

    public CastMember(int productionID, String jobTitle, int artistID){
        this.productionID = productionID;
        this.jobTitle = jobTitle;
        this.artistID = artistID;
        artist = getArtistFormDatabase();
    }

    public CastMember(String name, String jobTitle,int productionID){
        this.jobTitle = jobTitle;
        this.productionID = productionID;
        this.artist = DataFacade.getArtist(name);
        if (this.artist==null){
            DataFacade.insertArtist(new Artist(name));
            this.artist = DataFacade.getArtist(name);
        }
        this.artistID = artist.getId();
        DataFacade.insertCastMember(this);
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
}
