package dao;

import jakarta.validation.Valid;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
   LoginDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public boolean checkSamePassword(User user) {
        String sql = "SELECT * FROM public.Users WHERE username = ? AND password = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), user.getUsername(), user.getPassword());
        return !users.isEmpty();
    }

}
