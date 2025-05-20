package ru.azmeev.bank.blocker.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azmeev.bank.blocker.service.BlockerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blocker")
public class BlockerController {

    private final BlockerService blockerService;

    @PostMapping("/verify")
    public boolean verifyOperation() {
        return blockerService.verifyOperation();
    }
}