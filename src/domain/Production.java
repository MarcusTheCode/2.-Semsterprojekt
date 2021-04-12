package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Production implements Serializable {
    private long id;
    private String title;
    private String category;
    private ArrayList<CastMember> castMembers;


    public Production(long id,String title,String category){
        this.category = category;
        this.id = id;
        this.title = title;
        castMembers = new ArrayList<>();
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

    @Override
    public String toString() {
        // TODO: toString should return the credits in
        return super.toString();
    }
}


