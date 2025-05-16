package ru.azmeev.bank.front.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.azmeev.bank.front.web.dto.HasConfirmPassword;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, HasConfirmPassword> {

    @Override
    public boolean isValid(HasConfirmPassword value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String password = value.getPassword();
        String confirmPassword = value.getConfirmPassword();
        return password != null && password.equals(confirmPassword);
    }
}
