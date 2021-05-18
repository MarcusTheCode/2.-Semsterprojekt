package domain;

import data.DataFacade;

import java.io.Serializable;
import java.util.ArrayList;

public class Production implements Serializable {
    Integer id;
    String title;
    String category;
    ArrayList<CastMember> castMembers;
    private Integer episodeNumber;
    private String type;
    private Integer categoryID;
    private Integer seasonID;
    private Integer seasonNumber;
    private Integer producerID;
    private ArrayList<Genre> genres;

    public Production(int producerID) {
        this.producerID = producerID;
        this.category = "";
        this.title = "";
        this.episodeNumber = 0;
        this.type = "";
        this.seasonID = null;
        this.categoryID = null;
        this.castMembers = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    public Production(String title, Integer producerID, String category, Integer episodeNumber, String type) {
        this.title = title;
        this.producerID = producerID;
        this.category = category;
        this.episodeNumber = episodeNumber;
        this.type = type;
    }

    //Tv-Series
    public Production(int episodeNumber, Integer seasonsID, int producerID, int productionID, String title, String category, String type) {
        this.producerID = producerID;
        this.setCategory(category);
        this.id = productionID;
        this.title = title;
        this.episodeNumber = episodeNumber;
        if (seasonsID != 0)
            this.seasonID = seasonsID;
        this.type = type;
        this.castMembers = new ArrayList<>();
        this.genres = DataFacade.getGenres(productionID);
        this.seasonNumber = DataFacade.getSeasonNumber(seasonsID);
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }


    public void addCastMember(CastMember castMember) {
        castMembers.add(castMember);
    }

    public void removeCastMember(CastMember castMember) {
        castMembers.remove(castMember);
    }

    public ArrayList<CastMember> getCastMembers() {
        return castMembers;
    }
    public void setCastMembers(ArrayList<CastMember> castMembers) {
        this.castMembers = castMembers;
    }


    public boolean isOwner(SuperUser superUser) {
        return (producerID == superUser.getId());
    }

    public void setID(Integer ID) {
        this.id = ID;
    }

    public void setOwnerID(int ownerID) {
        this.producerID = ownerID;
    }

    public long getOwnerID() {
        return producerID;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCategory(String category) {
        this.category = category;
        this.categoryID = DataFacade.getCategory(category);
    }

    public String getCategory() {
        return category;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(int seasonsID) {
        this.seasonID = seasonsID;
    }

    public void setSeasonID(Integer seasonsID) {
        this.seasonID = seasonsID;
    }

    public int getProducerID() {
        return producerID;
    }

    public void setProducerID(int producerID) {
        this.producerID = producerID;
    }

    public void setProducerID(Integer producerID) {
        this.producerID = producerID;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}


