package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class CurrentTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTransactions;

    @Column
    private Double value;

    private Date time;

    private String description;

    @ManyToOne
    private Accounts account;

    @ManyToOne
    private Users user;

    public CurrentTransactions(Double value, Date time, String description, Accounts account, Users user) {
        this.value = value;
        this.time = time;
        this.description = description;
        this.account = account;
        this.user = user;
    }
}
