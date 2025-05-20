package ru.azmeev.bank.account.service;

import ru.azmeev.bank.account.web.dto.*;

import java.util.List;

public interface UserService {

    UserDto findUserByLogin(String login);

    List<UserDto> getUsersToTransfer(String login);

    void register(UserRegistrationDto dto);

    void editPassword(UserEditPasswordDto dto);

    void editAccounts(UserEditAccountsDto dto);

    Boolean cash(CashActionRequest dto);

    Boolean transfer(AccountTransferRequestDto dto);
}
