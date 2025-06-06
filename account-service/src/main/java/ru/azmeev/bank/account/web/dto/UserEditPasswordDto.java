package ru.azmeev.bank.account.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditPasswordDto {
    @NotNull
    @NotEmpty
    private String login;
    @NotNull
    private String password;
}
