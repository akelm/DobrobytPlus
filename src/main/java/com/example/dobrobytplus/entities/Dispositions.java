package com.example.dobrobytplus.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

/**
 * Dispositions - cyclic transactions under user control
 */
@Data
@NoArgsConstructor
@Entity
public class Dispositions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idDispositions;

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
     * Instantiates a new Dispositions.
     *
     * @param value       the value
     * @param time        the time
     * @param description the description
     * @param account     the account
     * @param user        the user
     */
    public Dispositions(Double value, Date time, String description, Accounts account, Users user) {
        this.value = value;
        this.time = time;
        this.description = description;
        this.account = account;
        this.user = user;
    }
}
