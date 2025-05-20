package ru.azmeev.bank.account.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.azmeev.bank.account.service.UserService;
import ru.azmeev.bank.account.web.dto.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{login}")
    public UserDto findUserByLogin(@PathVariable String login) {
        return userService.findUserByLogin(login);
    }

    @PostMapping("/editPassword")
    public void editPassword(@RequestBody UserEditPasswordDto dto) {
        userService.editPassword(dto);
    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserRegistrationDto dto) {
        userService.register(dto);
    }

    @PostMapping("/editAccounts")
    public void editAccounts(@RequestBody UserEditAccountsDto dto) {
        userService.editAccounts(dto);
    }

    @PostMapping("/cash")
    public Boolean cash(@RequestBody CashActionRequest dto) {
        return userService.cash(dto);
    }

    @PostMapping("/transfer")
    public Boolean transfer(@RequestBody AccountTransferRequestDto dto) {
        return userService.transfer(dto);
    }

    @GetMapping("/{login}/usersToTransfer")
    public List<UserDto> getUsersToTransfer(@PathVariable String login) {
        return userService.getUsersToTransfer(login);
    }
}