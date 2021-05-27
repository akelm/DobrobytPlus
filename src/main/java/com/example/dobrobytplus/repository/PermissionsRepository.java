package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.PermissionTypes;
import com.example.dobrobytplus.entities.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;


/**
 * The interface Permissions repository.
 */
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    /**
     * Find by account .
     *
     * @param account the account
     * @return the permissions
     */
    Permissions findByAccount(Accounts account);

    /**
     * Find by user username .
     *
     * @param username the username
     * @return the list
     */
    List<Permissions> findByUserUsername(String username);

    /**
     * Find by user username and account id  .
     *
     * @param username   the username
     * @param idAccounts the id accounts
     * @return the list
     */
    List<Permissions> findByUserUsernameAndAccount_IdAccounts(String username, Long idAccounts);

    /**
     * Find by user username and account  type .
     *
     * @param username    the username
     * @param accountType the account type
     * @return the list
     */
    List<Permissions> findByUserUsernameAndAccount_AccountType(String username, AccountTypes accountType);

    /**
     * Find by account id accounts and permission types .
     *
     * @param idAccount       the id account
     * @param permissionTypes the permission types
     * @return the list
     */
    List<Permissions> findByAccount_IdAccountsAndPermissionTypes(Long idAccount, PermissionTypes permissionTypes);

    /**
     * Count permissions by user birthdate   for account id   .
     *
     * @param bdate     the bdate
     * @param idAccount the id account
     * @return the int
     */
    int countPermissionsByUser_BirthdateIsAfterAndAccount_IdAccountsEquals(Date bdate, Long idAccount);

    /**
     * Count permissions by account id  and permission types .
     *
     * @param idAccount       the id account
     * @param permissionTypes the permission types
     * @return the int
     */
    int countPermissionsByAccount_IdAccountsAndPermissionTypes(Long idAccount, PermissionTypes permissionTypes);
}