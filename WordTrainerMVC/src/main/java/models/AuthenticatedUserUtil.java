package models;

import dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUserUtil {
    private static UserDAO userDAO;
    @Autowired
    public AuthenticatedUserUtil(UserDAO userDAO){
        AuthenticatedUserUtil.userDAO = userDAO;
    }
   private static final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public String getUsername(){
        return authentication.getName();
    }
    public int getUserId(){
        return userDAO.getUserIdByUsername(getUsername());
    }
    public User getAuthenticatedUser(){
        return userDAO.getUserByUsername(getUsername());
    }
}
