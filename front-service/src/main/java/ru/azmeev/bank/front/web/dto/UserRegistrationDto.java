package ru.azmeev.bank.front.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.azmeev.bank.front.web.validation.Adult;
import ru.azmeev.bank.front.web.validation.PasswordMatches;

import java.time.LocalDate;

@Getter
@Setter
@PasswordMatches
public class UserRegistrationDto implements HasConfirmPassword {
    @NotNull
    private String login;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    private String name;
    @NotNull
    @Adult
    private LocalDate birthdate;
}
