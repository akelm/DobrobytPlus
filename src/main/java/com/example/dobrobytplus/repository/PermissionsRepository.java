package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.PermissionTypes;
import com.example.dobrobytplus.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;


public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    Permissions findByAccount(Accounts account);
    List<Permissions> findByUserUsername(String username);
    List<Permissions> findByUserUsernameAndAccount_IdAccounts(String username, Long idAccounts);
    List<Permissions> findByUserUsernameAndAccount_AccountType(String username, AccountTypes accountType);
    List<Permissions> findByAccount_IdAccountsAndPermissionTypes(Long idAccount, PermissionTypes permissionTypes);
    int countPermissionsByUser_BirthdateIsAfterAndAccount_IdAccountsEquals(Date bdate, Long idAccount);
    int countPermissionsByAccount_IdAccountsAndPermissionTypes(Long idAccount, PermissionTypes permissionTypes);
}