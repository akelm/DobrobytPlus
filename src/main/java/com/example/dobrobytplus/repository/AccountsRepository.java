package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface Accounts repository.
 */
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    /**
     * Find by id  .
     *
     * @param id the id
     * @return the accounts
     */
    Accounts findByIdAccounts(Long id);


}