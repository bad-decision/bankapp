package ru.azmeev.bank.account.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditAccountsDto {
    @NotNull
    @NotEmpty
    private String login;
    private String name;
    private LocalDate birthdate;
    private List<String> account;
}
