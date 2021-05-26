package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.AccountsDto;
import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.dto.SaldaDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.*;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AccountsService {
    private final PermissionsRepository permissionsRepository;
    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;
    private final AutoDispositionsRepository autoDispositionsRepository;
    private final DispositionsRepository dispositionsRepository;
    private final CurrentTransactionsRepository currentTransactionsRepository;

    private boolean isAdult(String username) {
        Users user = usersRepository.findByUsername(username);
        Date userBirthdate = user.getBirthdate();
        Date user18Birthday = Date.valueOf(userBirthdate.toLocalDate().plusYears(18));
        Date today = new Date(System.currentTimeMillis());
        return user18Birthday.before(today) || user18Birthday.equals(today);
    }

    public Users getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((MyUsersPrincipal) principal).getUsername();
        return usersRepository.findByUsername(username);
    }

    /** potrzebny do MainController
     *
     * @return
     */
    public List<AccountTypes> accountsUserCanCreate() {
        Users user = getAuthenticatedUser();
        String username = user.getUsername();
        List<Permissions> permissionsForUser = permissionsRepository.findByUserUsername(username);
        List<AccountTypes> accountTypes = permissionsForUser
                .stream()
                .map(Permissions::getAccount)
                .map(Accounts::getAccountType)
                .collect(Collectors.toList());
        List<AccountTypes> typesUserCanCreate = new ArrayList<>();
        if (!accountTypes.contains(AccountTypes.PERSONAL)) {
            typesUserCanCreate.add(AccountTypes.PERSONAL);
        }
        if ( !accountTypes.contains(AccountTypes.COUPLE) && !accountTypes.contains(AccountTypes.FAMILY) && isAdult(username)) {
            typesUserCanCreate.add(AccountTypes.COUPLE);
        }
        return typesUserCanCreate;

    }


    /** zaklada nowy rachunek
     * bedzie potrzebne do main controller
     *
     * @param accountsDto
     */
    public void registerNewAccount(AccountsDto accountsDto) {
        Users user = getAuthenticatedUser();
        Accounts account = accountsRepository.save(new Accounts(accountsDto.getAccountType()));
        Permissions permission = new Permissions(account, user , PermissionTypes.OWNER);
        permissionsRepository.save(permission);
    }

    /** potrzebny do PersonalController
     *
     * @param idAccounts
     * @return
     */
    public String getAccountType(Long idAccounts) {
        Accounts account = accountsRepository.findByIdAccounts(idAccounts);
        return account.getAccountType().toString();

    }

    public Double plnToMikrosasin(double pln) {
        return pln/70;
    }

    public List<SaldaDto> sumAccount(Long idAccounts) {
        Accounts account = accountsRepository.findByIdAccounts(idAccounts);
        List<SaldaDto> saldaDtoList = new ArrayList<>();

        Double sumAcc1 = autoDispositionsRepository.sumAccount(account);
        sumAcc1 = (sumAcc1 == null)? 0D :sumAcc1;
        saldaDtoList.add(
                new SaldaDto(
                        "AUTO DISPOSITIONS",
                        sumAcc1,
                        plnToMikrosasin(sumAcc1)
                )
        );

        Double sumAcc2 = dispositionsRepository.sumAccount(account);
        sumAcc2 = (sumAcc2 == null)? 0D :sumAcc2;
        saldaDtoList.add(
                new SaldaDto(
                        "DISPOSITIONS",
                        sumAcc2,
                        plnToMikrosasin(sumAcc2)
                )
        );

        Double sumAcc3 = currentTransactionsRepository.sumAccount(account);
        sumAcc3 = (sumAcc3 == null)? 0D :sumAcc3;
        saldaDtoList.add(
                new SaldaDto(
                        "CURRENT TRANSACTIONS",
                        sumAcc3,
                        plnToMikrosasin(sumAcc3)
                )
        );

        double sumTot = sumAcc1+sumAcc2+sumAcc3;
        saldaDtoList.add(
                new SaldaDto(
                        "TOTAL:",
                        sumTot,
                        plnToMikrosasin(sumTot)
                )
        );

        return saldaDtoList;
    }


}
