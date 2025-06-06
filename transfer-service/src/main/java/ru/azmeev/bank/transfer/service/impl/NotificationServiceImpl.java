package ru.azmeev.bank.transfer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.transfer.service.NotificationService;
import ru.azmeev.bank.transfer.web.dto.TransferResultDto;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Value("${service.notification.url}")
    private String notificationUrl;
    private final RestClient restClient;

    @Override
    public void notify(TransferResultDto transferResultDto) {
        restClient.post()
                .uri(notificationUrl + "/api/notification/transfer")
                .body(transferResultDto)
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(Void.class);
    }
}
