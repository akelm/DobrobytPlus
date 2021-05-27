package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.AccountTypes;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for Accounts
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountsDto {
    private AccountTypes accountType;

}
