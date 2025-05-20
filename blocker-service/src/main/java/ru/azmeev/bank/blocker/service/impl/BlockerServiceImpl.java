package ru.azmeev.bank.blocker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azmeev.bank.blocker.service.BlockerService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BlockerServiceImpl implements BlockerService {

    @Override
    public boolean verifyOperation() {
        return true; //(new Random()).nextBoolean();
    }
}
