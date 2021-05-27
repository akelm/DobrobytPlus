package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.CurrentTransactionsDto;
import com.example.dobrobytplus.dto.DispositionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.exceptions.UserCannotDelete;
import com.example.dobrobytplus.exceptions.UserHasNoAccess;
import com.example.dobrobytplus.repository.*;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Dispositions service.
 */
@AllArgsConstructor
@Service
public class DispositionsService {
    private final PermissionsRepository permissionsRepository;
    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;
    private final DispositionsRepository dispositionsRepository;
    private final PermissionsService permissionsService;


    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((MyUsersPrincipal) principal).getUsername();
    }

    /**
     * Gets dispositions.
     *
     * @param idAccount the id account
     * @return the dispositions
     */
    public List<DispositionsDto> getDispositions(Long idAccount) {
        List<Dispositions> currentTransactions = dispositionsRepository.findDispositionsByAccount_IdAccounts(idAccount);
        return currentTransactions
                .stream()
                .map(DispositionsDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Can user delete list.
     *
     * @param idAccount           the id account
     * @param currentTransactions the current transactions
     * @return the list
     */
    public List<Boolean> canUserDelete(Long idAccount, List<DispositionsDto> currentTransactions) {
        String username = getAuthenticatedUsername();
        List<Permissions> permission = permissionsRepository.findByUserUsernameAndAccount_IdAccounts(username, idAccount);
        if (permission.size()<1) {
            throw new UserHasNoAccess();
        }
        PermissionTypes permissionType = permission.get(0).getPermissionTypes();

        if (permissionType == PermissionTypes.CHILD) {
            // tylko jego wlasne
            return currentTransactions.stream()
            .map(x -> x.getUsername().equals(username))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>(Collections.nCopies(currentTransactions.size(), true));
        }

    }

    /**
     * Delete dispositions.
     *
     * @param idAccount1     the id account 1
     * @param idDispositions the id dispositions
     */
// W 'personal.html' w tabelce 'DYSPOZYCJE' przycisk 'USUN' wywoluje
    // ta methode deleteDispositions. Jej cel: ma usunac dana dyspozycje z tabelki.
    // Parameter 'Long idAccount' moze niepotrzebne ale dodalem na wrazie czego.
    public void deleteDispositions(Long idAccount1, Long idDispositions) {
        String username = getAuthenticatedUsername();
        Dispositions dispositions = dispositionsRepository.findDispositionsByIdDispositions(idDispositions);
        Long idAccount = dispositions.getAccount().getIdAccounts();
        if (!permissionsService.doesUserHaveAccessToAccount(username, idAccount)) {
            throw new UserHasNoAccess();
        };
        PermissionTypes permissionTypes = permissionsService.userPermissionTypeInAccount(username,idAccount);

        if (permissionTypes == PermissionTypes.CHILD && !dispositions.getUser().getUsername().equals(username)) {
            throw new UserCannotDelete();
        }
        dispositionsRepository.delete(dispositions);

    }

    /**
     * Sum dispositions pln double.
     *
     * @param idAccount the id account
     * @return the double
     */
    public Double sumDispositionsPLN(Long idAccount){
        Accounts account = accountsRepository.findByIdAccounts(idAccount);
        Double sum= dispositionsRepository.sumAccount(account);
        if (sum == null) {
            sum = 0D;
        }
        return sum;
    }

    /**
     * Pln to mikrosasin double.
     *
     * @param pln the pln
     * @return the double
     */
    public Double plnToMikrosasin(double pln) {
        return pln/70;
    }


    /**
     * Save disposition.
     *
     * @param dispositionsDto the dispositions dto
     */
    public void saveDisposition(DispositionsDto dispositionsDto) {
        Accounts account = accountsRepository.findByIdAccounts(dispositionsDto.getIdAccount());
        Users user = usersRepository.findByUsername(dispositionsDto.getUsername());
        Dispositions disposition = new Dispositions(dispositionsDto.getValue(),
                dispositionsDto.getTime(),
                dispositionsDto.getDescription(),
                account,
                user);
        dispositionsRepository.saveAndFlush(disposition);
    }

}
