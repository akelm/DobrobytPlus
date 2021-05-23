package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.CurrentTransactions;
import com.example.dobrobytplus.entities.Dispositions;
import com.example.dobrobytplus.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DispositionsDto {
    private Double value;
    private Date time;
    private String description;
    private Long idAccount;
    private String username;

    public DispositionsDto(Dispositions disposition) {
        value = disposition.getValue();
        time = disposition.getTime();
        description = disposition.getDescription();
        idAccount = disposition.getAccount().getIdAccounts();
        username = disposition.getUser().getUsername();
    }
}
