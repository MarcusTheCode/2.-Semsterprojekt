package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Production implements Serializable {
    long id;
    String title;
    String category;
    ArrayList<CastMember> castMembers;


    public Production(long id,String title,String category){
        this.category = category;
        this.id = id;
        this.title = title;
        castMembers = new ArrayList<>();
    }

    @Override
    public String toString() {
        // TODO: toString should return the credits in
        return super.toString();
    }
}


