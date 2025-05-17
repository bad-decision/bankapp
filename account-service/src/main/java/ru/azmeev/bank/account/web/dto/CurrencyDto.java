package ru.azmeev.bank.account.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDto {
    private Long id;
    private String title;
    private String name;
}
