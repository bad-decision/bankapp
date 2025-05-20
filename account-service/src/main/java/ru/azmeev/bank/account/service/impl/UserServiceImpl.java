package ru.azmeev.bank.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azmeev.bank.account.model.AccountEntity;
import ru.azmeev.bank.account.model.CashAction;
import ru.azmeev.bank.account.model.CurrencyEntity;
import ru.azmeev.bank.account.model.UserEntity;
import ru.azmeev.bank.account.repository.AccountRepository;
import ru.azmeev.bank.account.repository.CurrencyRepository;
import ru.azmeev.bank.account.repository.UserRepository;
import ru.azmeev.bank.account.service.UserService;
import ru.azmeev.bank.account.web.dto.*;
import ru.azmeev.bank.account.web.mapper.UserMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto findUserByLogin(String login) {
        return userMapper.toDto(userRepository.findByLogin(login)
                .orElse(null));
    }

    @Override
    public List<UserDto> getUsersToTransfer(String login) {
        return userRepository.findAll()
                .stream()
                .filter(x -> !x.getLogin().equals(login))
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void register(UserRegistrationDto dto) {
        if (userRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        UserEntity user = UserEntity.builder()
                .login(dto.getLogin())
                .birthdate(dto.getBirthdate())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void editPassword(UserEditPasswordDto dto) {
        UserEntity user = userRepository.findByLogin(dto.getLogin())
                .orElseThrow(IllegalArgumentException::new);

        user.setPassword(dto.getPassword());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void editAccounts(UserEditAccountsDto dto) {
        UserEntity user = userRepository.findByLogin(dto.getLogin())
                .orElseThrow(IllegalArgumentException::new);

        if (StringUtils.isNotEmpty(dto.getName())) {
            user.setName(dto.getName());
        }
        if (dto.getBirthdate() != null) {
            user.setBirthdate(dto.getBirthdate());
        }
        userRepository.save(user);
        updateAccounts(user, dto.getAccount());
    }

    @Override
    @Transactional
    public Boolean cash(CashActionRequest dto) {
        UserEntity user = userRepository.findByLogin(dto.getLogin())
                .orElseThrow(IllegalArgumentException::new);
        AccountEntity account = user.getAccounts().stream()
                .filter(x -> x.getCurrency().getName().equals(dto.getCurrency()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        if (dto.getAction().equals(CashAction.GET)) {
            if (account.getValue().compareTo(dto.getValue()) > 0) {
                account.setValue(account.getValue().subtract(dto.getValue()));
                accountRepository.save(account);
                return true;
            }
        } else if (dto.getAction().equals(CashAction.PUT)) {
            account.setValue(account.getValue().add(dto.getValue()));
            accountRepository.save(account);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public Boolean transfer(AccountTransferRequestDto dto) {
        UserEntity fromUser = userRepository.findByLogin(dto.getFromLogin())
                .orElseThrow(IllegalArgumentException::new);
        AccountEntity fromAccount = fromUser.getAccounts().stream()
                .filter(x -> x.getCurrency().getName().equals(dto.getFromCurrency()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        UserEntity toUser = userRepository.findByLogin(dto.getToLogin())
                .orElseThrow(IllegalArgumentException::new);
        AccountEntity toAccount = toUser.getAccounts().stream()
                .filter(x -> x.getCurrency().getName().equals(dto.getToCurrency()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        if (fromAccount.getValue().compareTo(dto.getFromValue()) > 0) {
            fromAccount.setValue(fromAccount.getValue().subtract(dto.getFromValue()));
            toAccount.setValue(toAccount.getValue().add(dto.getToValue()));
            accountRepository.saveAll(List.of(fromAccount, toAccount));
            return true;
        }
        return false;
    }

    private void updateAccounts(UserEntity user, List<String> names) {
        List<CurrencyEntity> currency = currencyRepository.getCurrency(names);

        List<AccountEntity> accountsToAdd = currency.stream()
                .filter(x -> user.getAccounts().stream().noneMatch(y -> y.getCurrency().getName().equals(x.getName())))
                .map(currencyEntity -> AccountEntity.builder()
                        .value(BigDecimal.valueOf(0))
                        .currency(currencyEntity)
                        .user(user)
                        .build())
                .toList();

        List<AccountEntity> accountsToRemove = user.getAccounts().stream()
                .filter(x -> currency.stream().noneMatch(y -> y.getName().equals(x.getCurrency().getName())))
                .toList();

        accountRepository.deleteAll(accountsToRemove);
        accountRepository.saveAll(accountsToAdd);
    }
}