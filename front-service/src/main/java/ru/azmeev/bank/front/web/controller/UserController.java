package ru.azmeev.bank.front.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.azmeev.bank.front.model.UserEntity;
import ru.azmeev.bank.front.service.AccountService;
import ru.azmeev.bank.front.service.CashService;
import ru.azmeev.bank.front.service.TransferService;
import ru.azmeev.bank.front.service.UserService;
import ru.azmeev.bank.front.web.dto.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AccountService accountService;
    private final CashService cashService;
    private final TransferService transferService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserRegistrationDto dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", getValidationErrors(bindingResult));
            return "signup";
        }
        userService.register(dto);
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String mainScreen(@AuthenticationPrincipal UserEntity currentUser,
                             Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("accounts", accountService.getUserAccounts(currentUser.getLogin()));
        model.addAttribute("currency", accountService.getCurrency());
        model.addAttribute("users", userService.getUsersToTransfer());
        return "main";
    }

    @PostMapping("/editPassword")
    public String editPassword(@AuthenticationPrincipal UserEntity currentUser,
                               BindingResult bindingResult,
                               @Valid UserEditPasswordDto dto,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("passwordErrors", getValidationErrors(bindingResult));
            return "redirect:/main";
        }
        dto.setLogin(currentUser.getLogin());
        userService.editPassword(dto);
        return "redirect:/login";
    }

    @PostMapping("/editAccounts")
    public String editAccounts(@AuthenticationPrincipal UserEntity currentUser,
                               BindingResult bindingResult,
                               @Valid UserEditAccountsDto dto,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userAccountsErrors", getValidationErrors(bindingResult));
        } else {
            dto.setLogin(currentUser.getLogin());
            userService.editUser(currentUser.getLogin(), dto.getName(), dto.getBirthdate());
            accountService.editAccounts(dto);
        }
        return "redirect:/main";
    }

    @PostMapping("/cash")
    public String cash(@AuthenticationPrincipal UserEntity currentUser,
                       BindingResult bindingResult,
                       @Valid UserCashDto dto,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("cashErrors", getValidationErrors(bindingResult));
        } else {
            dto.setLogin(currentUser.getLogin());
            cashService.processCashAction(dto);
        }
        return "redirect:/main";
    }

    @PostMapping("/transfer")
    public String transfer(@AuthenticationPrincipal UserEntity currentUser,
                           BindingResult bindingResult,
                           @Valid UserTransferDto dto,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            String attribute = dto.getToLogin().equals(currentUser.getLogin()) ? "transferErrors" : "transferOtherErrors";
            redirectAttributes.addFlashAttribute(attribute, getValidationErrors(bindingResult));
        } else {
            dto.setFromLogin(currentUser.getLogin());
            transferService.processTransfer(dto);
        }
        return "redirect:/main";
    }

    private List<String> getValidationErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}