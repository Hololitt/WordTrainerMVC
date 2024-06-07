package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {
    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public User(){}
    @Column(name = "username")
    @Min(value = 3, message = "Too short nickname")
    @Max(12)

    @Pattern(regexp = "[A-Za-z]+", message = "Username must contain only letters")
    @NotEmpty(message="This field should be not empty")
    private String username;
    @Column(name = "email")
    @NotEmpty(message="This field should be not empty")
    @Email(message = "Incorrect form")
private String email;
    @Column(name = "password")
    @Min(value = 6, message = "Your password is too short")
    @NotEmpty(message = "Enter your password")
private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int id;
    public int getId(){
        return id;
    }

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
