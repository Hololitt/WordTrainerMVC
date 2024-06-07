package controllers;

import Validators.RegistrationFormValidator;
import dao.UserDAO;
import jakarta.validation.Valid;
import models.RegistrationForm;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/HomePage")
public class RegistrationController {
    private final RegistrationFormValidator registrationFormValidator;
    public RegistrationController(RegistrationFormValidator registrationFormValidator){
        this.registrationFormValidator = registrationFormValidator;
    }
    @Autowired
    private UserDAO userDAO;
    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }
@GetMapping("/success")
public String showSuccessPage(){
        return "successPage";
}
    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid RegistrationForm registrationForm, Model model){
        Errors errors = new BeanPropertyBindingResult(registrationForm, "registrationForm");
        registrationFormValidator.validate(registrationForm, errors);

        if(errors.hasErrors()){
            model.addAttribute("invalidRegistrationForm", "Your registration data are invalid");
            return "errorPage";
        }
        if(userDAO.isUsernameExists(registrationForm.getUsername()) && userDAO.isEmailExists(registrationForm.getEmail())){
            userDAO.addUser(new User(registrationForm.getUsername(),
                    registrationForm.getPassword(), registrationForm.getEmail()));
            model.addAttribute("successMessage","Successful registration");
            return "redirect:/HomePage/profile";
        }else{
model.addAttribute("unsuccessfulRegistration", "This account is already taken");
return "registration";
        }
    }
}
