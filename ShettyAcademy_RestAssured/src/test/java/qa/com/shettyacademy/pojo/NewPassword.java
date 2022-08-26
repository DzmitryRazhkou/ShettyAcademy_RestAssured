package qa.com.shettyacademy.pojo;

public class NewPassword {
    private String userEmail;
    private String userPassword;
    private String confirmPassword;

    public NewPassword(String userEmail, String userPassword, String confirmPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.confirmPassword = confirmPassword;
    }
}
