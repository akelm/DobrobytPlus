package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.PermissionTypes;
import com.example.dobrobytplus.entities.Permissions;
import com.example.dobrobytplus.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionsDto {
    private Accounts account;
    private Users user;
    private PermissionTypes permissionType;

    public PermissionsDto(Permissions permission) {
        account = permission.getAccount();
        user = permission.getUser();
        permissionType = permission.getPermissionTypes();
    }
}
