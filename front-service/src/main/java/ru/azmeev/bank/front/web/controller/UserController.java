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
import ru.azmeev.bank.front.model.Currency;
import ru.azmeev.bank.front.model.User;
import ru.azmeev.bank.front.service.CashService;
import ru.azmeev.bank.front.service.TransferService;
import ru.azmeev.bank.front.service.AccountService;
import ru.azmeev.bank.front.web.dto.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

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
        accountService.register(dto);
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String mainScreen(@AuthenticationPrincipal User currentUser,
                             Model model) {
        User user = accountService.findUserByLogin(currentUser.getLogin());
        List<User> usersToTransfer = accountService.getUsersToTransfer(currentUser.getLogin());
        List<Currency> currency = accountService.getCurrency();

        model.addAttribute("currentUser", user);
        model.addAttribute("accounts", user.getAccounts());
        model.addAttribute("currency", currency);
        model.addAttribute("users", usersToTransfer);
        return "main";
    }

    @PostMapping("/editPassword")
    public String editPassword(@AuthenticationPrincipal User currentUser,
                               @Valid UserEditPasswordDto dto,
                               RedirectAttributes redirectAttributes,
                               Model model,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("passwordErrors", getValidationErrors(bindingResult));
            return "redirect:/main";
        }
        dto.setLogin(currentUser.getLogin());
        accountService.editPassword(dto);
        return "redirect:/login";
    }

    @PostMapping("/editAccounts")
    public String editAccounts(@AuthenticationPrincipal User currentUser,
                               @Valid UserEditAccountsDto dto,
                               RedirectAttributes redirectAttributes,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userAccountsErrors", getValidationErrors(bindingResult));
        } else {
            dto.setLogin(currentUser.getLogin());
            accountService.editAccounts(dto);
        }
        return "redirect:/main";
    }

    @PostMapping("/cash")
    public String cash(@AuthenticationPrincipal User currentUser,
                       @Valid CashActionRequest dto,
                       RedirectAttributes redirectAttributes,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("cashErrors", getValidationErrors(bindingResult));
        } else {
            dto.setLogin(currentUser.getLogin());
            cashService.processCashAction(dto);
        }
        return "redirect:/main";
    }

    @PostMapping("/transfer")
    public String transfer(@AuthenticationPrincipal User currentUser,
                           @Valid UserTransferDto dto,
                           RedirectAttributes redirectAttributes,
                           BindingResult bindingResult) {
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