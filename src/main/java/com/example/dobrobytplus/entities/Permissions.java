package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPermissions;

    @ManyToOne
    private Accounts account;

    @ManyToOne
    private Users user;

    @ManyToOne
    private PermissionTypes permissionTypes;

    public Permissions(Accounts account, Users user, PermissionTypes permissionTypes) {
        this.account = account;
        this.user = user;
        this.permissionTypes = permissionTypes;
    }
}
