package qa.com.shettyacademy.pojo;

public class NewUser {
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userRole;
    private String occupation;
    private String gender;
    private String userMobile;
    private String userPassword;
    private String confirmPassword;
    private boolean required;

    public NewUser(String firstName, String lastName, String userEmail, String userRole, String occupation, String gender, String userMobile, String userPassword, String confirmPassword, boolean required) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.occupation = occupation;
        this.gender = gender;
        this.userMobile = userMobile;
        this.userPassword = userPassword;
        this.confirmPassword = confirmPassword;
        this.required = required;
    }
}
