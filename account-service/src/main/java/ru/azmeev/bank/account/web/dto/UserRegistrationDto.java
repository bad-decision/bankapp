package ru.azmeev.bank.account.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
