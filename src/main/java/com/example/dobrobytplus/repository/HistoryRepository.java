package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface HistoryRepository extends JpaRepository<History, Long> {
    History findByIdTransactions(Long id);
}