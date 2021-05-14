package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.PermissionTypes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface PermissionTypesRepository extends JpaRepository<PermissionTypes, Long> {
    PermissionTypes findByIdPermissionTypes(Long id);
}