package ru.azmeev.bank.cash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.cash.service.AccountService;
import ru.azmeev.bank.cash.web.dto.CashActionRequest;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Value("${service.account.url}")
    private String accountsUrl;
    private final RestClient restClient;

    @Override
    public boolean cashOperation(CashActionRequest dto) {
        return restClient.post()
                .uri(accountsUrl + "/api/user/cash")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Boolean.class);
    }
}
