package models;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
public class RegistrationForm {
    @NotEmpty(message = "Enter your nickname")
    @Min(value = 3, message = "Too short nickname")
    @Max(value = 12, message = "Too long nickname")
    private String username;

    @NotEmpty(message = "Enter your password")
    @Min(value = 6, message = "Too short password")
    private String password;

    @Email(message = "Invalid email address")
    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}