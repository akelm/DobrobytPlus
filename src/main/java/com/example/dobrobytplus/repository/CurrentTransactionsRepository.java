package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.CurrentTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface CurrentTransactionsRepository extends JpaRepository<CurrentTransactions, Long> {
    CurrentTransactions findByIdTransactions(Long id);
}