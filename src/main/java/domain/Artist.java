package domain;

import java.io.Serializable;

public class Artist implements Serializable {

    private int id;
    private String name;
    private String email;

    public Artist(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Artist(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "(" + id + ") " + name + " - " + email;
    }
}
