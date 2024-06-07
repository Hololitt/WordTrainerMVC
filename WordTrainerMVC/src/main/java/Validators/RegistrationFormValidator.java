package Validators;

import models.RegistrationForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RegistrationFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {

        return RegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
        RegistrationForm registrationForm = (RegistrationForm) target;
String username = registrationForm.getUsername();
        if(registrationForm.getPassword().length() < 6){
            errors.rejectValue("password", "field.minlength");
        }
        if(username.length() < 3 || username.length() > 12){
            errors.rejectValue("username", "field.invalid");
        }
    }
}
