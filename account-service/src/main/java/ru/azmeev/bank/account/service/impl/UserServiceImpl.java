package ru.azmeev.bank.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azmeev.bank.account.model.AccountEntity;
import ru.azmeev.bank.account.model.CurrencyEntity;
import ru.azmeev.bank.account.model.UserEntity;
import ru.azmeev.bank.account.repository.AccountRepository;
import ru.azmeev.bank.account.repository.CurrencyRepository;
import ru.azmeev.bank.account.repository.UserRepository;
import ru.azmeev.bank.account.service.UserService;
import ru.azmeev.bank.account.web.dto.UserDto;
import ru.azmeev.bank.account.web.dto.UserEditAccountsDto;
import ru.azmeev.bank.account.web.dto.UserEditPasswordDto;
import ru.azmeev.bank.account.web.dto.UserRegistrationDto;
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
                .orElseThrow(IllegalArgumentException::new));
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