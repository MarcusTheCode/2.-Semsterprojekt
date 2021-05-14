import domain.CastMember;

public class TestCastMember extends CastMember {

    public TestCastMember(int productionID, String jobTitle, int artistID) {
        super(productionID, jobTitle, artistID);
    }

    public TestCastMember(String name, String email, String jobTitle, int productionID) {
        super(name, email, jobTitle, productionID);
    }

    public TestCastMember(CastMember castMember) {
        super(castMember.getName(), castMember.getEmail(), castMember.getJobTitle(), castMember.getProductionID());
    }

    public boolean equals(CastMember castMember) {
        if (this.getName() == castMember.getName()
            && this.getJobTitle() == castMember.getJobTitle()
            && this.getEmail() == castMember.getEmail()) {
            return true;
        }
        return false;
    }
}
