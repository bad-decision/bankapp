package ru.azmeev.bank.account.service;

import ru.azmeev.bank.account.model.UserEntity;
import ru.azmeev.bank.account.web.dto.UserDto;
import ru.azmeev.bank.account.web.dto.UserEditAccountsDto;
import ru.azmeev.bank.account.web.dto.UserEditPasswordDto;
import ru.azmeev.bank.account.web.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {

    UserDto findUserByLogin(String login);

    List<UserDto> getUsersToTransfer(String login);

    void register(UserRegistrationDto dto);

    void editPassword(UserEditPasswordDto dto);

    void editAccounts(UserEditAccountsDto dto);
}
