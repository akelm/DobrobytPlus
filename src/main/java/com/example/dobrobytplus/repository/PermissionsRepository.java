package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    Permissions findByAccount(Long id);
}