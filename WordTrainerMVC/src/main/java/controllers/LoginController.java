package controllers;

import dao.LoginDAO;
import jakarta.validation.Valid;
import models.LoginForm;
import models.User;
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
    @GetMapping
    public String showLoginForm(Model model){
        model.addAttribute("user", new LoginForm());
        return "login";
    }
    @PostMapping
public String login(@ModelAttribute("user") @Valid LoginForm loginForm,
                    BindingResult bindingResult, Model model, LoginDAO loginDAO){
if(bindingResult.hasErrors()){
    model.addAttribute("unsuccessfulMessage", "Login or password is wrong");
    return "login";
}
User user = new User();
user.setPassword(loginForm.getPassword());
user.setUsername(loginForm.getUsername());
        if(loginDAO.checkSamePassword(user)){
    model.addAttribute("successfulMessage", "Successful login");
    return "redirect:/profile";
        }
model.addAttribute("UnsuccessfulMessage", "Login or password is wrong");
return "redirect:/login";

    }
}
