package controllers;

import dao.UserDAO;
import models.AuthenticatedUserUtil;
import models.LanguageCard;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/HomePage/profile")
public class ProfileController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AuthenticatedUserUtil authenticatedUserUtil;

    @GetMapping
    public String showUserProfile(Model model){
User user = authenticatedUserUtil.getAuthenticatedUser();
model.addAttribute(user.getEmail());
model.addAttribute(userDAO.getLearnedWordsByUserId(user.getId()).size());
model.addAttribute(user.getUsername());
        return "profile";
    }

    @GetMapping("/myLanguageCards")
    public String showLanguageCards(Model model){
        List<LanguageCard> languageCards = userDAO.getLearnedWordsByUserId(authenticatedUserUtil.getUserId());
        model.addAttribute(languageCards);
        model.addAttribute(userDAO.getLessRepetitiveLanguageCardsByUser(authenticatedUserUtil.getUsername()));
                model.addAttribute(userDAO.getMostDifficultLanguageCardsByUser(authenticatedUserUtil.getUsername()));
        return "myLanguageCards";
    }
}
