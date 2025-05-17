package ru.azmeev.bank.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.front.model.Account;
import ru.azmeev.bank.front.model.Currency;
import ru.azmeev.bank.front.model.User;
import ru.azmeev.bank.front.service.AccountService;
import ru.azmeev.bank.front.web.dto.UserEditAccountsDto;
import ru.azmeev.bank.front.web.dto.UserEditPasswordDto;
import ru.azmeev.bank.front.web.dto.UserRegistrationDto;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Value("${service.account.url}")
    private String accountUrl;
    private final RestClient restClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserByLogin(String login) {
        User user = restClient.get()
                .uri(accountUrl + "/api/user/" + login)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(User.class);

        List<Currency> currency = getCurrency();
        List<Account> accounts = currency.stream()
                .map(x -> user.getAccounts().stream()
                        .filter(y -> y.getCurrency().getName().equals(x.getName()))
                        .peek(y -> y.setExists(true))
                        .findFirst()
                        .orElse(Account.builder()
                                .value(BigDecimal.valueOf(0))
                                .exists(false)
                                .currency(x)
                                .build())
                )
                .toList();
        user.setAccounts(accounts);
        return user;
    }

    @Override
    public void register(UserRegistrationDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        restClient.post()
                .uri(accountUrl + "/api/user/signup")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);
    }

    @Override
    public List<User> getUsersToTransfer(String login) {
        return restClient.get()
                .uri(accountUrl + "/api/user/" + login + "/usersToTransfer")
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public void editPassword(UserEditPasswordDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        restClient.post()
                .uri(accountUrl + "/api/user/editPassword")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);
    }

    @Override
    public void editAccounts(UserEditAccountsDto dto) {
        restClient.post()
                .uri(accountUrl + "/api/user/editAccounts")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);
    }

    @Override
    public List<Currency> getCurrency() {
        return restClient.get()
                .uri(accountUrl + "/api/currency")
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
