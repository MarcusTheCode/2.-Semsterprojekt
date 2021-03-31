package domain;

import java.util.ArrayList;
import java.util.List;

public class Production {
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
}


