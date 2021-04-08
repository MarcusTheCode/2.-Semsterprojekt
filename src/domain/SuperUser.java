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
    public String getUsername() { return username;}
    public String getPassword() { return password;}

    public boolean isSysAdmin() {
        return sysAdmin;
    }
}
