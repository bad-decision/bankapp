package ru.azmeev.bank.exchange.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "BANK_EXCHANGE_RATES")
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "value", nullable = false)
    private BigDecimal value;
}
