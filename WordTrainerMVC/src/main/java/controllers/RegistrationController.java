package controllers;

import dao.UserDAO;
import jakarta.validation.Valid;
import models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/HomePage")
public class RegistrationController {
    private UserDAO userDAO;
    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "Registration";
    }
@GetMapping("/success")
public String showSuccessPage(){
        return "SuccessPage";
}
    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user,
                            BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/HomePage/registration";
        }
        if(userDAO.isUsernameExists(user.getUsername()) && userDAO.isEmailExists(user.getEmail())){
            userDAO.addUser(user);
            model.addAttribute("successMessage","Successful registration");
        }else{
model.addAttribute("unsuccessfulRegistration", "This account is already taken");
return "redirect:/HomePage/register";
        }
        return "redirect:/HomePage";
    }
}
