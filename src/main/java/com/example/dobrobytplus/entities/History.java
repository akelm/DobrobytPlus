package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTransactions;

    @Column
    private Double value;

    @Column
    private Date time;

    @Column
    private String description;

    @ManyToOne
    private Accounts account;

    @ManyToOne
    private Users user;

    public History(Double value, Date time, String description, Accounts account, Users user) {
        this.value = value;
        this.time = time;
        this.description = description;
        this.account = account;
        this.user = user;
    }


    public History(AutoDispositions dispositions) {
        LocalDate today =  dispositions.getTime().toLocalDate();
        LocalDate ld = LocalDate.of(today.getYear(), today.getMonth() , 1);
        Date dayStart = Date.valueOf(ld);

        this.value = dispositions.getValue();
        this.time = dayStart;
        this.description = dispositions.getDescription();
        this.account = dispositions.getAccount();
        this.user = dispositions.getUser();
    }

    public History(Dispositions dispositions) {
        LocalDate today =  dispositions.getTime().toLocalDate();
        LocalDate ld = LocalDate.of(today.getYear(), today.getMonth() , 1);
        Date dayStart = Date.valueOf(ld);


        this.value = dispositions.getValue();
        this.time = dayStart;
        this.description = dispositions.getDescription();
        this.account = dispositions.getAccount();
        this.user = dispositions.getUser();
    }

    public History(CurrentTransactions dispositions) {
        this.value = dispositions.getValue();
        this.time = dispositions.getTime();
        this.description = dispositions.getDescription();
        this.account = dispositions.getAccount();
        this.user = dispositions.getUser();
    }

}
