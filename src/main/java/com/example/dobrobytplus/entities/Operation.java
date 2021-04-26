package com.example.dobrobytplus.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = false)
    private Double amount;

    @Column(nullable = false)
    private Date time;

    @ManyToOne
    private User user;


    public Operation(Double amount, Date time, User user) {
        this.amount = amount;
        this.time = time;
        this.user = user;
    }
}
