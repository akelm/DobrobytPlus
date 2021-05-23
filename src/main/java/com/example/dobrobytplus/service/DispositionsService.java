package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.CurrentTransactionsDto;
import com.example.dobrobytplus.dto.DispositionsDto;
import com.example.dobrobytplus.entities.*;
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

@AllArgsConstructor
@Service
public class DispositionsService {
    private final PermissionsRepository permissionsRepository;
    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;
    private final DispositionsRepository dispositionsRepository;


    private String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((MyUsersPrincipal) principal).getUsername();
    }

    public List<DispositionsDto> getDispositions(Long idAccount) {
        List<Dispositions> currentTransactions = dispositionsRepository.findDispositionsByAccount_IdAccounts(idAccount);
        return currentTransactions
                .stream()
                .map(DispositionsDto::new)
                .collect(Collectors.toList());
    }

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

    public Double sumDispositionsPLN(Long idAccount){
        Accounts account = accountsRepository.findByIdAccounts(idAccount);
        return dispositionsRepository.sumAccount(account);
    }

    public Double plnToMikrosasin(double pln) {
        return pln/70;
    }


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
