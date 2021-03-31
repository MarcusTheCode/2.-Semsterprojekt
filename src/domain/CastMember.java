package domain;

public class CastMember {
    private String name;
    private String jobTitle;

    public CastMember(String name, String jobTitle){
        this.jobTitle = jobTitle;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
