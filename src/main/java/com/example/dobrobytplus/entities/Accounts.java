package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * Account for transaction managenemt
 */
@Data
@NoArgsConstructor
@Entity
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAccounts;

    @Column(columnDefinition = "ENUM('PERSONAL', 'COUPLE', 'FAMILY')")
    @Enumerated(EnumType.STRING)
    private AccountTypes accountType;

    /**
     * Instantiates a new Accounts.
     *
     * @param accountType the account type
     */
    public Accounts(AccountTypes accountType) {
        this.accountType = accountType;
    }
}
