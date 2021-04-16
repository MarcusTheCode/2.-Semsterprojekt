package domain;

import java.io.Serializable;

public class SuperUser implements Serializable {
    String password;
    String username;
    long id;
    boolean sysAdmin;

    public SuperUser(long id, String password, String username, boolean sysAdmin) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.sysAdmin = sysAdmin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSysAdmin(boolean sysAdmin) {
        this.sysAdmin = sysAdmin;
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
