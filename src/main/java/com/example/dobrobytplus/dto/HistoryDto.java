package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.History;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Data transfer object for History
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryDto {
    private Double value;
    private Date time;
    private String description;
    private String username;
    private Long accountId;

    /**
     * Instantiates a new History dto.
     *
     * @param history the history
     */
    public HistoryDto(History history) {
        value = history.getValue();
        time = history.getTime();
        description = history.getDescription();
        username = history.getUser().getUsername();
        accountId = history.getAccount().getIdAccounts();
    }
}
