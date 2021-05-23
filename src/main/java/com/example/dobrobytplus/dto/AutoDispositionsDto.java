package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.AutoDispositions;
import com.example.dobrobytplus.entities.Dispositions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutoDispositionsDto {
    private Double value;
    private Date time;
    private String description;
    private Long idAccount;
    private String username;

    public AutoDispositionsDto(AutoDispositions disposition) {
        value = disposition.getValue();
        time = disposition.getTime();
        description = disposition.getDescription();
        idAccount = disposition.getAccount().getIdAccounts();
        username = disposition.getUser().getUsername();
    }
}
