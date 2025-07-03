package ru.azmeev.bank.blocker.service.impl;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.blocker.service.BlockerService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BlockerServiceImpl implements BlockerService {
    private final MeterRegistry meterRegistry;

    @Override
    public boolean verifyOperation() {
        boolean allowedOperation = (new Random()).nextBoolean();
        if (!allowedOperation) {
            meterRegistry.counter("blocked-operation").increment();
        }
        return allowedOperation;
    }
}
