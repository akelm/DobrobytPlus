package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface UsersRepository extends JpaRepository<Users, Long> {


    Users findByUsername(String username);

}