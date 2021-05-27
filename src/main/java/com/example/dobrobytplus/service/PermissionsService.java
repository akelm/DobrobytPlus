package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.exceptions.AccountNotFoundException;
import com.example.dobrobytplus.exceptions.UserIsAlreadyAPartner;
import com.example.dobrobytplus.exceptions.UserNotAdultException;
import com.example.dobrobytplus.exceptions.UsernameNotFoundException;
import com.example.dobrobytplus.repository.AccountsRepository;
import com.example.dobrobytplus.repository.PermissionsRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Permissions service.
 */
@AllArgsConstructor
@Service
public class PermissionsService {
    private final PermissionsRepository permissionsRepository;
    private final UsersRepository usersRepository;
    private final AccountsRepository accountsRepository;
    private final AutoDispositionsService autoDispositionsService;

    /**
     * Gets current username.
     *
     * @return the current username
     */
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((MyUsersPrincipal) principal).getUsername();
    }

    private boolean isAdult(String username) {
        Users user = usersRepository.findByUsername(username);
        Date userBirthdate = user.getBirthdate();
        Date user18Birthday = Date.valueOf(userBirthdate.toLocalDate().plusYears(18));
        Date today = new Date(System.currentTimeMillis());
        return user18Birthday.before(today) || user18Birthday.equals(today);
    }

    /**
     * Gets user permissions.
     *
     * @return the user permissions
     */
    public List<PermissionsDto> getUserPermissions() {
        String username = getCurrentUsername();
        List<Permissions> permissionsForUser = permissionsRepository.findByUserUsername(username);
        List<PermissionsDto> permissionsForUserDto = new ArrayList<>();
        for (Permissions perm : permissionsForUser) {
            permissionsForUserDto.add(new PermissionsDto(perm));
        }
        return permissionsForUserDto;
    }

    /**
     * To bedzie potrzebne w MembershipController
     *
     * @param username  the username
     * @param accountId the account id
     */
    public void addChildToAccount(String username, Long accountId) {
        Users user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException();
        }

        Accounts account = accountsRepository.findByIdAccounts(accountId);
        if (account == null) {
            throw new AccountNotFoundException();
        }
        Permissions permission = new Permissions(account, user, PermissionTypes.CHILD);
        permissionsRepository.save(permission);
        updateAfterChildChange(accountId);
    }


    private void updateAfterChildChange(Long idAccount) {
        autoDispositionsService.updateAutoDisposition(idAccount);
        int numChild = permissionsRepository.countPermissionsByAccount_IdAccountsAndPermissionTypes(idAccount,PermissionTypes.CHILD);
        Accounts account = accountsRepository.findByIdAccounts(idAccount);
        if (numChild > 0) {
            account.setAccountType(AccountTypes.FAMILY);
        } else {
            account.setAccountType(AccountTypes.COUPLE);
        }

        accountsRepository.save(account);
    }


    /**
     * To bedzie potrzebne w MembershipController
     *
     * @param username  the username
     * @param accountId the account id
     */
    public void addCPartnerToAccount(String username, Long accountId) {
        Users user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException();
        }
        if (!isAdult(user.getUsername())) {
            throw new UserNotAdultException();
        }
        // blad, gdy uzytkownik jest juz w jakims koncie jako partner
        List<Permissions> userPermissions = permissionsRepository.findByUserUsername(user.getUsername());
        List<PermissionTypes> permissionTypes = userPermissions
                .stream()
                .map(Permissions::getPermissionTypes)
                .collect(Collectors.toList());
        if (permissionTypes.contains(PermissionTypes.PARTNER)) {
            throw new UserIsAlreadyAPartner();
        }

        Accounts account = accountsRepository.findByIdAccounts(accountId);
        if (account == null) {
            throw new AccountNotFoundException();
        }
        Permissions permission = new Permissions(account, user, PermissionTypes.PARTNER);
        permissionsRepository.save(permission);
    }

    /**
     * usuwa uprawnienia do konta
     * do uzycia w MembershipController
     *
     * @param username  the username
     * @param accountId the account id
     */
    public void revokeAccessToAccount(String username, Long accountId) {
//        Users user = usersRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException();
//        }
//        Accounts account = accountsRepository.findByIdAccounts(accountId);
//        if (account == null) {
//            throw new AccountNotFoundException();
//        }
        List<Permissions> permission = permissionsRepository.findByUserUsernameAndAccount_IdAccounts(username, accountId);
        for (Permissions perm : permission) {
            permissionsRepository.delete(perm);
        }
        updateAfterChildChange(accountId);
    }

    /**
     * sprawdza, czy mozna uzytkownikowi pokazac rachunek
     * potrzebne do MainController albo do PersonalController
     *
     * @param username  the username
     * @param accountId the account id
     * @return boolean
     */
    public boolean doesUserHaveAccessToAccount(String username, Long accountId) {
        List<Permissions> permission = permissionsRepository.findByUserUsernameAndAccount_IdAccounts(username, accountId);
        return permission.size() > 0;
    }

    /**
     * Does current user have access to account boolean.
     *
     * @param accountId the account id
     * @return the boolean
     */
    public boolean doesCurrentUserHaveAccessToAccount(Long accountId) {
        return doesUserHaveAccessToAccount(getCurrentUsername(), accountId);
    }

    /**
     * potrzebne do personalController
     *
     * @param accountId the account id
     * @return string
     */
    public String currentUserRoleInAccount(Long accountId ) {
        String username = getCurrentUsername();
        return userRoleInAccount(username, accountId);
    }

    /**
     * User permission type in account permission types.
     *
     * @param username  the username
     * @param accountId the account id
     * @return the permission types
     */
    public PermissionTypes userPermissionTypeInAccount(String username, Long accountId ) {
        List<Permissions> permission = permissionsRepository.findByUserUsernameAndAccount_IdAccounts(username, accountId);
        if (permission.size() < 1) {
            return null;
        } else {
            return permission.get(0).getPermissionTypes();
        }
    }


    /**
     * User role in account string.
     *
     * @param username  the username
     * @param accountId the account id
     * @return the string
     */
    public String userRoleInAccount(String username, Long accountId ) {
        PermissionTypes permissionTypes = userPermissionTypeInAccount(username,accountId);
        if (permissionTypes == null) {
            return "";
        } else {
            return permissionTypes.toString();
        }
    }

    /**
     * potrzebne do MembershipController
     *
     * @param idAccounts the id accounts
     * @return account owner
     */
    public String getAccountOwner(Long idAccounts) {
        List<Permissions> permissions = permissionsRepository.findByAccount_IdAccountsAndPermissionTypes(idAccounts, PermissionTypes.OWNER);
        return permissions.get(0).getUser().getUsername();
    }

    /**
     * potrzebne do MembershipController
     *
     * @param idAccounts the id accounts
     * @return account partner
     */
    public String getAccountPartner(Long idAccounts) {
        List<Permissions> permissions = permissionsRepository.findByAccount_IdAccountsAndPermissionTypes(idAccounts, PermissionTypes.PARTNER);
        if (permissions.size() <1) {
            return "";
        } else {
        return permissions.get(0).getUser().getUsername();
        }
    }

    /**
     * potrzebne do MembershipController
     *
     * @param idAccounts the id accounts
     * @return account children
     */
    public List<String> getAccountChildren(Long idAccounts) {
        List<Permissions> permissions = permissionsRepository.findByAccount_IdAccountsAndPermissionTypes(idAccounts, PermissionTypes.CHILD);
        return permissions.stream().map(x -> x.getUser().getUsername()).collect(Collectors.toList());
    }

}
