package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * History - the non editable log of past transactions
 */
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Accounts account;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users user;

    /**
     * Instantiates a new History.
     *
     * @param value       the value
     * @param time        the time
     * @param description the description
     * @param account     the account
     * @param user        the user
     */
    public History(Double value, Date time, String description, Accounts account, Users user) {
        this.value = value;
        this.time = time;
        this.description = description;
        this.account = account;
        this.user = user;
    }


    /**
     * Instantiates a new History.
     *
     * @param dispositions the dispositions
     */
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

    /**
     * Instantiates a new History.
     *
     * @param dispositions the dispositions
     */
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

    /**
     * Instantiates a new History.
     *
     * @param dispositions the dispositions
     */
    public History(CurrentTransactions dispositions) {
        this.value = dispositions.getValue();
        this.time = dispositions.getTime();
        this.description = dispositions.getDescription();
        this.account = dispositions.getAccount();
        this.user = dispositions.getUser();
    }

}
