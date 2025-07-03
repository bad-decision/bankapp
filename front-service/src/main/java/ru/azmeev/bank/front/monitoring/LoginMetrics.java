package ru.azmeev.bank.front.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginMetrics {
    private final MeterRegistry meterRegistry;

    public void incrementSuccess(String username) {
        meterRegistry.counter("auth_login_success",
                        "login", username)
                .increment();
    }

    public void incrementFailure(String username) {
        meterRegistry.counter("auth_login_failure",
                        "login", username)
                .increment();
    }
}