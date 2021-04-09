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

    public long getOwnerID() {
        return ownerID;
    }
    
    public boolean isOwner(SuperUser user) {
        return getOwnerID() == user.getId();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        // TODO: toString should return the credits in
        return super.toString();
    }
}


