package dao;

import models.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    LoginDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkSamePassword(LoginForm loginForm) {
        String hashedPassword = passwordEncoder.encode(loginForm.getPassword());
        String sql = "SELECT username, password FROM public.Users WHERE username = ?";
       LoginForm loginFormExists = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(LoginForm.class),
               loginForm.getUsername(), hashedPassword);
       if(loginFormExists != null){
           return loginFormExists.getPassword().equals(hashedPassword);
       }else{
           return false;
       }
    }
}