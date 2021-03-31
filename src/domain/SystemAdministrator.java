package domain;

public class SystemAdministrator extends SuperUser{
    private String password;
    private String username;
    private long id;

    SystemAdministrator(long id, String password, String username) {
        super(id, password, username);
        this.id = id;
        this.password = password;
        this.username = username;
    }
}
