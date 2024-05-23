package models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class LoginForm {
    @NotEmpty(message = "Enter your nickname")
    @Min(value = 3, message = "Too short nickname")
    @Max(value = 12, message = "Too long nickname")
    private String username;
    @NotEmpty(message = "Enter your nickname")
    @Min(value = 6, message = "Too short password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
