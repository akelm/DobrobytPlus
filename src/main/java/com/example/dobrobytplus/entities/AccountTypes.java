package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class AccountTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAccountTypes;

    @Column
    private String accountType;

    public AccountTypes(String accountType) {
        this.accountType = accountType;
    }
}
