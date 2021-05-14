package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Accounts findByIdAccounts(Long id);
}