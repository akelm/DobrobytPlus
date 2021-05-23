package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    Permissions findByAccount(Accounts account);
    List<Permissions> findByUserUsername(String username);
    List<Permissions> findByUserUsernameAndAccount_IdAccounts(String username, Long idAccounts);
    List<Permissions> findByUserUsernameAndAccount_AccountType(String username, AccountTypes accountType);

}