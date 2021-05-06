package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAccounts;

    @ManyToOne
    private AccountTypes accountType;

    @ManyToOne
    private Users owner;

    public Accounts(AccountTypes accountType, Users owner) {
        this.accountType = accountType;
        this.owner = owner;
    }
}
