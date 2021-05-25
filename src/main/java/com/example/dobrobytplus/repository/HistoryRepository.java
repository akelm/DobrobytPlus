package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface HistoryRepository extends JpaRepository<History, Long> {
    History findByIdTransactions(Long id);
    List<History> findByAccount_IdAccounts(Long idAccount);
    List<History> findByTimeBetween(Date timeStart, Date timeEnd);
}