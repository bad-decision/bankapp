package ru.azmeev.bank.account.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.azmeev.bank.account.service.UserService;
import ru.azmeev.bank.account.web.dto.UserDto;
import ru.azmeev.bank.account.web.dto.UserEditAccountsDto;
import ru.azmeev.bank.account.web.dto.UserEditPasswordDto;
import ru.azmeev.bank.account.web.dto.UserRegistrationDto;

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

    @GetMapping("/{login}/usersToTransfer")
    public List<UserDto> getUsersToTransfer(@PathVariable String login) {
        return userService.getUsersToTransfer(login);
    }
}