package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Dispositions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface DispositionsRepository extends JpaRepository<Dispositions, Long> {
    Dispositions findByIdCyclicTransactions(Long id);
}