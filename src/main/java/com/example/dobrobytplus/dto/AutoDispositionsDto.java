package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.AutoDispositions;
import com.example.dobrobytplus.entities.Dispositions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Data transfer object for  Auto dispositions.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutoDispositionsDto {
    private Double value;
    private Date time;
    private String description;
    private Long idAccount;
    private String username;

    /**
     * Instantiates a new Auto dispositions dto.
     *
     * @param disposition the disposition
     */
    public AutoDispositionsDto(AutoDispositions disposition) {
        value = disposition.getValue();
        time = disposition.getTime();
        description = disposition.getDescription();
        idAccount = disposition.getAccount().getIdAccounts();
        username = disposition.getUser().getUsername();
    }
}
