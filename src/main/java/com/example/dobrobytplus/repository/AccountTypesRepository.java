package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.AccountTypes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface AccountTypesRepository extends JpaRepository<AccountTypes, Long> {
    Accounts findByIdAccountTypes(Long id);
}