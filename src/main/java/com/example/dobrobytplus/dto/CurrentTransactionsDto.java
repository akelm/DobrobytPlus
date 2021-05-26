package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.CurrentTransactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentTransactionsDto {
    private Long id;
    private Double value;
    private Date time;
    private String description;
    private Long idAccount;
    private String username;

    public CurrentTransactionsDto(CurrentTransactions currentTransactions) {
        id = currentTransactions.getIdTransactions();
        value = currentTransactions.getValue();
        time = currentTransactions.getTime();
        description = currentTransactions.getDescription();
        idAccount = currentTransactions.getAccount().getIdAccounts();
        username = currentTransactions.getUser().getUsername();
    }
}
