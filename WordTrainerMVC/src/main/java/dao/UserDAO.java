package dao;

import jakarta.validation.Valid;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {
    @Autowired
    UserDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
private final JdbcTemplate jdbcTemplate;
    public void addUser(@Valid User user){
        jdbcTemplate.update("INSERT INTO Users(username, password, email) VALUES(?, ?, ?)",
                user.getUsername(), user.getPassword(), user.getEmail());
    }
    public boolean isUsernameExists(String username) {
        String sql = "SELECT * FROM public.Users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        return users.isEmpty();
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT * FROM public.Users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email);
        return users.isEmpty();
    }
}
