package controllers;

import dao.LoginDAO;
import jakarta.validation.Valid;
import models.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final LoginDAO loginDAO;
    @Autowired
    public LoginController(LoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }
    @GetMapping
    public String showLoginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "loginForm";
    }
    @PostMapping
public String login(@ModelAttribute("user") @Valid LoginForm loginForm,
                    BindingResult bindingResult, Model model){
if(bindingResult.hasErrors()){
    model.addAttribute("unsuccessfulMessage", "Login or password is wrong");
    return "login";
}
        if(loginDAO.checkSamePassword(loginForm)){
    model.addAttribute("successfulMessage", "Successful login");
    return "redirect:/profile";
        }else{
            model.addAttribute("unsuccessfulMessage", "Login or password is wrong");
            return "login";
        }

    }
}
