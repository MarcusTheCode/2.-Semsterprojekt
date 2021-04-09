package domain;

import java.io.Serializable;

public class SuperUser implements Serializable {
    String password;
    String username;
    long id;
    boolean sysAdmin;

    SuperUser(long id, String password, String username, boolean sysAdmin) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.sysAdmin = sysAdmin;
    }

    // Unused for now
    /*public void addProduction(Production production){
        productions.add(production);
    }

    public void addProduction(List<Production> productionsList){
        productions.addAll(productionsList);
    }*/

    public long getId() {
        return id;
    }

    public boolean isSysAdmin() {
        return sysAdmin;
    }
}
