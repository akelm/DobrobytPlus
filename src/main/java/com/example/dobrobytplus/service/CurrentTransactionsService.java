package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.CurrentTransactionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.exceptions.UserCannotDelete;
import com.example.dobrobytplus.exceptions.UserHasNoAccess;
import com.example.dobrobytplus.repository.AccountsRepository;
import com.example.dobrobytplus.repository.CurrentTransactionsRepository;
import com.example.dobrobytplus.repository.PermissionsRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The  Current transactions service.
 */
@AllArgsConstructor
@Service
public class CurrentTransactionsService {
    private final PermissionsRepository permissionsRepository;
    /**
     * The Accounts repository.
     */
    public final AccountsRepository accountsRepository;
    private final CurrentTransactionsRepository currentTransactionsRepository;
    private final UsersRepository usersRepository;
    private final PermissionsService permissionsService;


    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((MyUsersPrincipal) principal).getUsername();
    }

    /**
     * Gets current transactions.
     *
     * @param idAccount the id account
     * @return the current transactions
     */
    public List<CurrentTransactionsDto> getCurrentTransactions(Long idAccount) {
        List<CurrentTransactions> currentTransactions = currentTransactionsRepository.findCurrentTransactionsByAccount_IdAccounts(idAccount);
        return currentTransactions
                .stream()
                .map(CurrentTransactionsDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Can user delete a transaction.
     *
     * @param idAccount           the id account
     * @param currentTransactions the current transactions
     * @return the list
     */
    public List<Boolean> canUserDelete(Long idAccount, List<CurrentTransactionsDto> currentTransactions) {
        String username = getAuthenticatedUsername();
        List<Permissions> permission = permissionsRepository.findByUserUsernameAndAccount_IdAccounts(username, idAccount);
        if (permission.size()<1) {
            throw new UserHasNoAccess();
        }
        PermissionTypes permissionType = permission.get(0).getPermissionTypes();

        if (permissionType == PermissionTypes.CHILD) {
            // tylko jego wlasne
            return currentTransactions.stream()
            .map(x -> x. getUsername().equals(username))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>(Collections.nCopies(currentTransactions.size(), true));
        }

    }

    /**
     * Sum current transactions in pln .
     *
     * @param idAccount the id account
     * @return the double
     */
    public Double sumCurrentTransactionsPLN(Long idAccount){
        Accounts account = accountsRepository.findByIdAccounts(idAccount);
        Double sum = currentTransactionsRepository.sumAccount(account);
        if (sum == null) {
            sum = 0D;
        }
        return sum;
    }

    /**
     * Pln to mikrosasin .
     *
     * @param pln the pln
     * @return the double
     */
    public Double plnToMikrosasin(double pln) {
        return pln/70;
    }

    /**
     * Save current transaction.
     *
     * @param currentTransactionsDto the current transactions dto
     */
    public void saveCurrentTransaction(CurrentTransactionsDto currentTransactionsDto) {
        Accounts account = accountsRepository.findByIdAccounts(currentTransactionsDto.getIdAccount());
        Users user = usersRepository.findByUsername(currentTransactionsDto.getUsername());
        CurrentTransactions currentTransaction = new CurrentTransactions(currentTransactionsDto.getValue(),
                currentTransactionsDto.getTime(),
                currentTransactionsDto.getDescription(),
                account,
                user);
        currentTransactionsRepository.saveAndFlush(currentTransaction);
    }

    /**
     * Delete current transaction.
     *
     * @param idCurrentTransaction the id current transaction
     */
    public void deleteCurrentTransaction(Long idCurrentTransaction) {
        String username = getAuthenticatedUsername();
        CurrentTransactions currentTransactions = currentTransactionsRepository.findCurrentTransactionsByIdTransactions(idCurrentTransaction);
        Long idAccount = currentTransactions.getAccount().getIdAccounts();
        if (!permissionsService.doesUserHaveAccessToAccount(username, idAccount)) {
            throw new UserHasNoAccess();
        };
        PermissionTypes permissionTypes = permissionsService.userPermissionTypeInAccount(username,idAccount);

        if (permissionTypes == PermissionTypes.CHILD && !currentTransactions.getUser().getUsername().equals(username)) {
            throw new UserCannotDelete();
        }
        currentTransactionsRepository.delete(currentTransactions);

    }

}
