package ru.azmeev.bank.front.service;

import ru.azmeev.bank.front.model.Currency;
import ru.azmeev.bank.front.model.User;
import ru.azmeev.bank.front.web.dto.UserEditAccountsDto;
import ru.azmeev.bank.front.web.dto.UserEditPasswordDto;
import ru.azmeev.bank.front.web.dto.UserRegistrationDto;

import java.util.List;

public interface AccountService {

    void register(UserRegistrationDto dto);

    void editPassword(UserEditPasswordDto dto);

    void editAccounts(UserEditAccountsDto dto);

    User findUserByLogin(String login);

    List<User> getUsersToTransfer(String login);

    List<Currency> getCurrency();
}
