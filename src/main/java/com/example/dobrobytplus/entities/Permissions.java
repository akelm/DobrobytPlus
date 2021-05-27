package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Permissions to accounts
 */
@Data
@NoArgsConstructor
@Entity
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPermissions;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accounts account;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users user;

    @Column(columnDefinition = "ENUM('OWNER', 'PARTNER', 'CHILD')")
    @Enumerated(EnumType.STRING)
    private PermissionTypes permissionTypes;

    /**
     * Instantiates a new Permissions.
     *
     * @param account         the account
     * @param user            the user
     * @param permissionTypes the permission types
     */
    public Permissions(Accounts account, Users user, PermissionTypes permissionTypes) {
        this.account = account;
        this.user = user;
        this.permissionTypes = permissionTypes;
    }
}
