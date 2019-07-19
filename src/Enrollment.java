/**
 * Enrollment class
 *
 * @author Dau lam
 * @Date 07/19/2019
 */
public class Enrollment implements Comparable {

    private String userId;
    private String firstName;
    private String lastName;
    private int version;
    private String insuranceCompany;


    public Enrollment(String userId, String firstName, String lastName, int version, String insuranceCompany) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.version = version;
        this.insuranceCompany = insuranceCompany;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    @Override
    public int compareTo(Object o) {
        // sort by last and first name
        Enrollment otherEnrollment = (Enrollment)o;
        String otherLastFirstName = otherEnrollment.getLastName() + otherEnrollment.getFirstName();
        String thisLastFirstName = this.getLastName() + this.getFirstName();
        return thisLastFirstName.compareTo(otherLastFirstName);
    }

    @Override
    public String toString() {
        return "[UserId = " + this.userId + "," +
                " \tFirstLastName = " + this.getFirstName() + " " + this.getLastName() + "," +
                " \tVersion = " + this.version + "," +
                " \tInsuranceCompany = " + this.getInsuranceCompany() + "]";
    }
}
