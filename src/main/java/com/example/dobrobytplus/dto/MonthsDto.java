package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.History;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonthsDto {
    private Double valuePLN;
    private Double valueMikroSasin;
    private String month;

    public MonthsDto(String s) {
        month = s;
        valuePLN =  0D;
        valueMikroSasin =  0D;
    }
}
