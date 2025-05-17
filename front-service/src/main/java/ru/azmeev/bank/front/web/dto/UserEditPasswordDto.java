package ru.azmeev.bank.front.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.azmeev.bank.front.web.validation.PasswordMatches;

@Getter
@Setter
@PasswordMatches
public class UserEditPasswordDto implements HasConfirmPassword {
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
}
