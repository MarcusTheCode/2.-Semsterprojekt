package domain;

public class SuperUser {
    String password;
    String username;
    long id;
    boolean sysAdmin;

    SuperUser(long id, String password, String username){
        this.id = id;
        this.password = password;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public boolean isSysAdmin() {
        return sysAdmin;
    }
}
