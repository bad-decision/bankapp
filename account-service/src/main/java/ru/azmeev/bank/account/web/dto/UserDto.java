package ru.azmeev.bank.account.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String name;
    private LocalDate birthdate;
    private List<AccountDto> accounts;
}
