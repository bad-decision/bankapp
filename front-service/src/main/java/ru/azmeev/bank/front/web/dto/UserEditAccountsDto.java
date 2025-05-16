package ru.azmeev.bank.front.web.dto;

import lombok.Getter;
import lombok.Setter;
import ru.azmeev.bank.front.web.validation.Adult;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserEditAccountsDto {
    private String name;
    private String login;
    @Adult(required = false)
    private LocalDate birthdate;
    private List<String> account;
}
