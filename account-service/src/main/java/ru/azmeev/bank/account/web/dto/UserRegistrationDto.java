package ru.azmeev.bank.account.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegistrationDto {
    @NotNull
    private String login;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birthdate;
}
