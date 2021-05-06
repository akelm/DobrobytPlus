package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.History;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryDto {
    private Double value;
    private Date time;
    private String description;

    public HistoryDto(History history) {
        value = history.getValue();
        time = history.getTime();
        description = history.getDescription();
    }
}
