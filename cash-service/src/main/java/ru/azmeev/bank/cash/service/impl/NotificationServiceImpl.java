package ru.azmeev.bank.cash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.cash.service.NotificationService;
import ru.azmeev.bank.cash.web.dto.CashActionResult;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Value("${service.notifications.url}")
    private String notificationsUrl;
    private final RestClient restClient;

    @Override
    public void notify(CashActionResult cashActionResult) {
        restClient.post()
                .uri(notificationsUrl + "/api/notification/cash")
                .body(cashActionResult)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);
    }
}
