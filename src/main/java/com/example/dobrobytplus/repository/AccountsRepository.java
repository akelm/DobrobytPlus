package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Accounts findByIdAccounts(Long id);
}