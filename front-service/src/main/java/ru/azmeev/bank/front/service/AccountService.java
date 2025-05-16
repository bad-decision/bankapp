package ru.azmeev.bank.front.service;

import ru.azmeev.bank.front.model.Account;
import ru.azmeev.bank.front.model.Currency;
import ru.azmeev.bank.front.web.dto.UserEditAccountsDto;

import java.util.List;

public interface AccountService {

    List<Account> getUserAccounts(String login);

    void editAccounts(UserEditAccountsDto dto);

    List<Currency> getCurrency();
}
