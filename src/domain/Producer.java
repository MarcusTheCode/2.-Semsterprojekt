package domain;

public class Producer extends SuperUser{
    private String password;
    private String username;
    private long id;

    Producer(long id, String password, String username) {
        super(id, password, username);
        this.id = id;
        this.password = password;
        this.username = username;
    }
}
