package com.example.dobrobytplus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaldaDto {
    private String tableName;
    private Double valuePLN;
    private Double valueMikroSasin;

}
