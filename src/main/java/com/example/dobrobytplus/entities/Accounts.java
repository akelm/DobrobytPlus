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

    @Column(columnDefinition = "ENUM('PERSONAL', 'COUPLE', 'FAMILY')")
    @Enumerated(EnumType.STRING)
    private AccountTypes accountType;
//
//    @ManyToOne
//    private Users owner;

    public Accounts(AccountTypes accountType) {
        this.accountType = accountType;
//        this.owner = owner;
    }
}
