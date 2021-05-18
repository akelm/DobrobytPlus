package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    Permissions findByAccount(Accounts account);
    List<Permissions> findByUserUsername(String username);
}