package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Production implements Serializable {
    long id;
    String title;
    String category;
    ArrayList<CastMember> castMembers;
    long ownerID;

    public Production(long ownerID, long id, String title, String category) {
        this.ownerID = ownerID;
        this.category = category;
        this.id = id;
        this.title = title;
        castMembers = new ArrayList<>();
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
}


