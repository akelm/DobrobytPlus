package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.CurrentTransactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentTransactionsDto {
    private Double value;
    private Date time;
    private String description;

    public CurrentTransactionsDto(CurrentTransactions currentTransactions) {
        value = currentTransactions.getValue();
        time = currentTransactions.getTime();
        description = currentTransactions.getDescription();
    }
}
