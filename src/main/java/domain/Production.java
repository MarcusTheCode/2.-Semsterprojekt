package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Production implements Serializable {
    int id;
    String title;
    String category;
    ArrayList<CastMember> castMembers;
    int ownerID;



    private Integer episodeNumber;
    private String type;
    private Integer categoryID;
    private Integer seasonsID;
    private Integer producerID;
    private ArrayList<String> genres;

    public Production(int ownerID) {
        this.ownerID = ownerID;
        this.category = "category";
        this.title = "title";
        castMembers = new ArrayList<>();
        genres = new ArrayList<>();
    }

    //Movies
    public Production(int ownerID, int productionID, String title, String category) {
        this.ownerID = ownerID;
        this.category = category;
        this.id = productionID;
        this.title = title;
        castMembers = new ArrayList<>();
        genres = new ArrayList<>();
    }

    //Tv-Series
    public Production(int episodeNumber, int seasonsID, int producerID, int productionID, String title, String category, String type) {
        this.ownerID = producerID;
        this.category = category;
        this.id = productionID;
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.seasonsID = seasonsID;
        this.type = type;
        castMembers = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void addCastMember(CastMember castMember) {
        castMembers.add(castMember);
    }

    public boolean isOwner(SuperUser superUser) {
        return (ownerID == superUser.getId());
    }

    public ArrayList<CastMember> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(ArrayList<CastMember> castMembers) {
        this.castMembers = castMembers;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static String getMetaData(){
        // TODO: Add metadata
        return new String("No metadata available");
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getSeasonsID() {
        return seasonsID;
    }

    public void setSeasonsID(int seasonsID) {
        this.seasonsID = seasonsID;
    }

    public int getProducerID() {
        return producerID;
    }

    public void setProducerID(int producerID) {
        this.producerID = producerID;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public void setSeasonsID(Integer seasonsID) {
        this.seasonsID = seasonsID;
    }

    public void setProducerID(Integer producerID) {
        this.producerID = producerID;
    }
}


