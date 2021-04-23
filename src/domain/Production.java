package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Production implements Serializable {
    long id;
    String title;
    String category;
    ArrayList<CastMember> castMembers;
    long ownerID;

    public Production(long ownerID) {
        this.ownerID = ownerID;
        this.category = "category";
        this.title = "title";
        castMembers = new ArrayList<>();

    }

    public Production(long ownerID, long id, String title, String category) {
        this.ownerID = ownerID;
        this.category = category;
        this.id = id;
        this.title = title;
        castMembers = new ArrayList<>();
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

    public void setID(long ID) {
        this.id = ID;
    }

    public void setOwnerID(long ownerID) {
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

    public long getId() {
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
}


