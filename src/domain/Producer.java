package domain;

import java.util.ArrayList;
import java.util.List;

public class Producer extends SuperUser{
    private String password;
    private String username;
    private long id;
    private List<Production> productions;

    public Producer(long id, String password, String username) {
        super(id, password, username);
        this.id = id;
        this.password = password;
        this.username = username;
        productions = new ArrayList<>();
    }

    public void addProduction(Production production){
        productions.add(production);
    }

    public void addProduction(List<Production> productionsList){
        productions.addAll(productionsList);
    }

}
