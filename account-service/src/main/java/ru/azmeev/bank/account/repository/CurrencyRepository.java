package ru.azmeev.bank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.azmeev.bank.account.model.CurrencyEntity;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    @Query("select e from CurrencyEntity e where e.name in :names")
    List<CurrencyEntity> getCurrency(List<String> names);
}
