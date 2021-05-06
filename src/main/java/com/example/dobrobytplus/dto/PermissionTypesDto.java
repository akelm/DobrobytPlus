package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.PermissionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionTypesDto {
    private String permissionType;

    public PermissionTypesDto(PermissionTypes permissionTypes) {
        permissionType = permissionTypes.getPermissionType();
    }
}
