package models;

import jakarta.validation.constraints.NotEmpty;

public class LoginForm {
    @NotEmpty(message = "Enter your nickname")
    private String username;
    @NotEmpty(message = "Enter your password")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
