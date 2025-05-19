package ru.azmeev.bank.cash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.cash.service.CashService;
import ru.azmeev.bank.cash.web.dto.CashActionRequest;
import ru.azmeev.bank.cash.web.dto.CashActionResult;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@RequiredArgsConstructor
@Service
public class CashServiceImpl implements CashService {

    @Value("${service.blocker.url}")
    private String blockerUrl;
    @Value("${service.notifications.url}")
    private String notificationsUrl;
    @Value("${service.account.url}")
    private String accountsUrl;
    private final RestClient restClient;

    @Override
    public CashActionResult process(CashActionRequest dto) {
        Boolean isAllowed = restClient.post()
                .uri(blockerUrl + "/api/blocker/verify")
                .body(dto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Boolean.class);

        CashActionResult cashActionResult;
        if (isAllowed) {
            Boolean success = restClient.post()
                    .uri(accountsUrl + "/api/user/cash")
                    .body(dto)
                    .attributes(clientRegistrationId("keycloak"))
                    .retrieve()
                    .body(Boolean.class);
            cashActionResult = CashActionResult.builder()
                    .success(success)
                    .message(success ? "Cash operation was successful" : "Cash operation was not successful")
                    .build();
        } else {
            cashActionResult = CashActionResult.builder()
                    .success(false)
                    .message("Cash operation is not allowed")
                    .build();
        }

        restClient.post()
                .uri(notificationsUrl + "/api/notification/cash")
                .body(cashActionResult)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);

        return cashActionResult;
    }
}
