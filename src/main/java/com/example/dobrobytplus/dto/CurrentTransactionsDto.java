package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.CurrentTransactions;
import com.example.dobrobytplus.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentTransactionsDto {
    private Double value;
    private Date time;
    private String description;
    private Long idAccount;
    private String username;

    public CurrentTransactionsDto(CurrentTransactions currentTransactions) {
        value = currentTransactions.getValue();
        time = currentTransactions.getTime();
        description = currentTransactions.getDescription();
        idAccount = currentTransactions.getAccount().getIdAccounts();
        username = currentTransactions.getUser().getUsername();
    }
}
