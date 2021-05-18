package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.PermissionsDto;
import com.example.dobrobytplus.entities.*;
import com.example.dobrobytplus.repository.PermissionsRepository;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MainService {
    private final PermissionsRepository permissionsRepository;
    private final UsersRepository usersRepository;

    private boolean isAdult(String username) {
        Users user = usersRepository.findByUsername(username);
        Date userBirthdate = user.getBirthdate();
        Date user18Birthday = Date.valueOf(userBirthdate.toLocalDate().plusYears(18));
        Date today = new Date(System.currentTimeMillis());
        return user18Birthday.before(today) || user18Birthday.equals(today);
    }

    public List<PermissionsDto> getUserPermissions() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((MyUsersPrincipal) principal).getUsername();
        List<Permissions> permissionsForUser = permissionsRepository.findByUserUsername(username);
        List<PermissionsDto> permissionsForUserDto = new ArrayList<>();
        for (Permissions perm : permissionsForUser) {
            permissionsForUserDto.add(new PermissionsDto(perm));
        }
        return permissionsForUserDto;
    }

    public List<AccountTypes> accountsUserCanCreate() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((MyUsersPrincipal) principal).getUsername();
        List<Permissions> permissionsForUser = permissionsRepository.findByUserUsername(username);
        List<AccountTypes> permissionTypes = permissionsForUser
                .stream()
                .map(Permissions::getAccount)
                .map(Accounts::getAccountType)
                .collect(Collectors.toList());
        List<AccountTypes> typesUserCanCreate = new ArrayList<>();
        if (!permissionTypes.contains(AccountTypes.PERSONAL)) {
            typesUserCanCreate.add(AccountTypes.PERSONAL);
        }
        if ( !permissionTypes.contains(AccountTypes.COUPLE) && !permissionTypes.contains(AccountTypes.FAMILY) && isAdult(username)) {
            typesUserCanCreate.add(AccountTypes.COUPLE);
        }
        return typesUserCanCreate;

    }

}
