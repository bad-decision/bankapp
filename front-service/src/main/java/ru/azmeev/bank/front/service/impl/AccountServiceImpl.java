package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.front.model.Account;
import ru.azmeev.bank.front.model.Currency;
import ru.azmeev.bank.front.service.AccountService;
import ru.azmeev.bank.front.web.dto.UserEditAccountsDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Override
    public List<Account> getUserAccounts(String login) {
        return null;
    }

    @Override
    public void editAccounts(UserEditAccountsDto dto) {

    }

    @Override
    public List<Currency> getCurrency() {
        return null;
    }
}
