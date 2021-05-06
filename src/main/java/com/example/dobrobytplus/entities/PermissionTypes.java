package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class PermissionTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPermissionTypes;

    @Column
    private String permissionType;

    public PermissionTypes(String permissionType) {
        this.permissionType = permissionType;
    }
}
