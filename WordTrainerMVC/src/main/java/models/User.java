package models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class User {
    @Min(value = 3, message = "Too short nickname")
    @Max(12)
    @NotEmpty(message="This field should be not empty")
    private String username;
    @NotEmpty(message="This field should be not empty")
    @Email(message = "Incorrect form")
private String email;
    @Min(value = 6, message = "Your password is too short")
    @NotEmpty(message = "Enter your password")
private String password;
    public void setUsername(String username){
        this.username = username;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}
