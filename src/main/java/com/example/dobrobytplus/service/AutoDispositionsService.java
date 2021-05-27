package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.AutoDispositionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.exceptions.OwnerNotFoundException;
import com.example.dobrobytplus.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The  Auto dispositions service.
 */
@AllArgsConstructor
@Service
public class AutoDispositionsService {
    private final PermissionsRepository permissionsRepository;
    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;
    private final AutoDispositionsRepository autoDispositionsRepository;

    private static final double naDziecko = 500D;
    private static final String descriptionNaDziecko = "Rodzina 500+";

    /**
     * gets autodipositions
     *
     * @param idAccount the id account
     * @return auto dispositions
     */
    public List<AutoDispositionsDto> getAutoDispositions(Long idAccount) {
        List<AutoDispositions> currentTransactions = autoDispositionsRepository.findAutoDispositionsByAccount_IdAccounts(idAccount);
        return currentTransactions
                .stream()
                .map(AutoDispositionsDto::new)
                .collect(Collectors.toList());
    }

    /**
     * gets sum of autodispositions
     *
     * @param idAccount the id account
     * @return double
     */
    public Double sumAutoDispositionsPLN(Long idAccount){
        Accounts account = accountsRepository.findByIdAccounts(idAccount);
        Double sum = autoDispositionsRepository.sumAccount(account);
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
     * updates autodispositions with 500+ per child
     *
     * @param idAccount the id account
     */
    public void updateAutoDisposition(Long idAccount) {
        Accounts account = accountsRepository.findByIdAccounts(idAccount);

        // wlasciciel konta
        List<Permissions> ownerList = permissionsRepository.findByAccount_IdAccountsAndPermissionTypes(idAccount, PermissionTypes.OWNER);
        if (ownerList.size() < 1) {
            throw new OwnerNotFoundException();
        }
        Users user = ownerList.get(0).getUser();
        // zbiera info o dzieciach
        Date today = new Date(System.currentTimeMillis());
        Date oldestBirthdate = Date.valueOf(today.toLocalDate().minusYears(18));
        int childNo  =
            permissionsRepository.countPermissionsByUser_BirthdateIsAfterAndAccount_IdAccountsEquals(oldestBirthdate,idAccount);
        double value = childNo*naDziecko;
        List<AutoDispositions> rowsNaDziecko = autoDispositionsRepository.findAutoDispositionsByAccountAndDescription(account,descriptionNaDziecko);

        if (rowsNaDziecko.size() <1) {
            AutoDispositions autoDisp = new AutoDispositions(value, today, descriptionNaDziecko, account, user);
            autoDispositionsRepository.save(autoDisp);
        } else {
            AutoDispositions autoDisp = rowsNaDziecko.get(0);
            autoDisp.setValue(value);
            autoDispositionsRepository.save(autoDisp);
        }


    }

}
