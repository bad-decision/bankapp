package ru.azmeev.bank.account.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.azmeev.bank.account.model.AccountEntity;
import ru.azmeev.bank.account.model.CashAction;
import ru.azmeev.bank.account.model.CurrencyEntity;
import ru.azmeev.bank.account.model.UserEntity;
import ru.azmeev.bank.account.repository.AccountRepository;
import ru.azmeev.bank.account.repository.CurrencyRepository;
import ru.azmeev.bank.account.repository.UserRepository;
import ru.azmeev.bank.account.service.impl.UserServiceImpl;
import ru.azmeev.bank.account.web.dto.*;
import ru.azmeev.bank.account.web.mapper.UserMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private AccountRepository accountRepository;
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @InjectMocks
    private UserServiceImpl userService;

    private static final String USER_LOGIN = "user01";
    private UserEntity user;

    @AfterAll
    static void resetMocks() {
        Mockito.reset();
    }

    @BeforeEach
    void prepare() {
        CurrencyEntity currency = CurrencyEntity.builder()
                .title("USD")
                .name("Dollar")
                .id(1L)
                .build();

        AccountEntity account = AccountEntity.builder()
                .id(1L)
                .user(user)
                .value(BigDecimal.valueOf(100))
                .currency(currency)
                .build();

        user = UserEntity.builder()
                .id(1L)
                .login(USER_LOGIN)
                .name(USER_LOGIN)
                .birthdate(LocalDate.of(2000, 1, 1))
                .password("pass")
                .accounts(List.of(account))
                .build();
    }

    @Test
    void findUserByLogin_success() {
        when(userRepository.findByLogin(USER_LOGIN)).thenReturn(Optional.of(user));
        UserDto result = userService.findUserByLogin(USER_LOGIN);
        Assertions.assertEquals(user.getLogin(), result.getLogin());
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getName(), result.getName());
        Assertions.assertEquals(user.getBirthdate(), result.getBirthdate());
        verify(userRepository, times(1)).findByLogin(USER_LOGIN);
    }

    @Test
    void getUsersToTransfer_success() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDto> result = userService.getUsersToTransfer(USER_LOGIN);
        Assertions.assertEquals(0, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void register_success() {
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .login("newUser")
                .birthdate(LocalDate.of(2000, 1, 1))
                .name("name")
                .password("pass")
                .build();

        when(userRepository.findByLogin(dto.getLogin())).thenReturn(Optional.empty());
        userService.register(dto);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void register_error() {
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .login(USER_LOGIN)
                .birthdate(LocalDate.of(2000, 1, 1))
                .name("name")
                .password("pass")
                .build();

        when(userRepository.findByLogin(dto.getLogin())).thenReturn(Optional.of(user));
        Assertions.assertThrows(RuntimeException.class, () -> userService.register(dto));
    }

    @Test
    void editPassword_success() {
        UserEditPasswordDto dto = UserEditPasswordDto.builder()
                .login(USER_LOGIN)
                .password("pass")
                .build();

        when(userRepository.findByLogin(dto.getLogin())).thenReturn(Optional.of(user));
        userService.editPassword(dto);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void editAccounts_success() {
        UserEditAccountsDto dto = UserEditAccountsDto.builder()
                .login(USER_LOGIN)
                .birthdate(LocalDate.of(2000, 1, 1))
                .name("updatedName")
                .build();

        when(userRepository.findByLogin(dto.getLogin())).thenReturn(Optional.of(user));
        when(currencyRepository.getCurrency(any())).thenReturn(new ArrayList<>());
        userService.editAccounts(dto);
        verify(userRepository, times(1)).save(any());
        verify(accountRepository, times(1)).deleteAll(any());
        verify(accountRepository, times(1)).saveAll(any());
    }

    @Test
    void cash_success() {
        CashActionRequest dto = CashActionRequest.builder()
                .login(USER_LOGIN)
                .action(CashAction.PUT)
                .login(USER_LOGIN)
                .value(BigDecimal.valueOf(100))
                .currency(user.getAccounts().get(0).getCurrency().getName())
                .build();

        when(userRepository.findByLogin(dto.getLogin())).thenReturn(Optional.of(user));
        userService.cash(dto);
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void transfer_success() {
        AccountTransferRequestDto dto = AccountTransferRequestDto.builder()
                .fromLogin(USER_LOGIN)
                .fromCurrency(user.getAccounts().get(0).getCurrency().getName())
                .toLogin(USER_LOGIN)
                .toCurrency(user.getAccounts().get(0).getCurrency().getName())
                .fromValue(BigDecimal.valueOf(100))
                .toValue(BigDecimal.valueOf(100))
                .build();

        when(userRepository.findByLogin(dto.getFromLogin())).thenReturn(Optional.of(user));
        Boolean result = userService.transfer(dto);
        Assertions.assertFalse(result);
        verify(accountRepository, times(0)).saveAll(any());
    }
}