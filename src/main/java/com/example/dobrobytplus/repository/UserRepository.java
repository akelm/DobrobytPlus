package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * tutaj dajemy metody pobierania danych itp customowego zapisu
 */
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);

}