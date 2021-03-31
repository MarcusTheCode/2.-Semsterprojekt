package domain;

public class SuperUser {
    String password;
    String username;
    long id;

    SuperUser(long id, String password, String username){
        this.id = id;
        this.password = password;
        this.username = username;
    }
    
}
