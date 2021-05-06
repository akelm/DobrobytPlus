package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.AccountTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountTypesDto {
    private String accountType;

    public AccountTypesDto(AccountTypes accountTypes) {
        accountType = accountTypes.getAccountType();
    }
}
