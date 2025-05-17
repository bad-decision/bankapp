package ru.azmeev.bank.account.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserEditAccountsDto {
    @NotNull
    @NotEmpty
    private String login;
    private String name;
    private LocalDate birthdate;
    private List<String> account;
}
