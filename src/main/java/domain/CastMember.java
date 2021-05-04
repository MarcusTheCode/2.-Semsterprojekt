package domain;

import java.io.Serializable;

public class CastMember implements Serializable {

    private int id;
    private String name;
    private String jobTitle;

    public CastMember(int id, String name, String jobTitle){
        this.id = id;
        this.jobTitle = jobTitle;
        this.name = name;
    }

    public CastMember(String name, String jobTitle){
        this.jobTitle = jobTitle;
        this.name = name;
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

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
