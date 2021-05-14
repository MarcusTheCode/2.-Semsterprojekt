package domain;

import data.DataFacade;
import java.io.Serializable;

public class CastMember implements Serializable {

    private int productionID;
    private String jobTitle;
    private String name;
    private String email;

    public CastMember(int productionID, String jobTitle, int artistID) {
        this.productionID = productionID;
        this.jobTitle = jobTitle;
    }

    // TODO: change getArtist(name) to getArtist(email) after implementation of email in production.fxml
    public CastMember(String name, String email, String jobTitle, int productionID) {
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.productionID = productionID;
    }

    public int getProductionID() {
        return productionID;
    }

    public int getId() {
        return productionID;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setProductionID(int productionID) {
        this.productionID = productionID;
    }
}
