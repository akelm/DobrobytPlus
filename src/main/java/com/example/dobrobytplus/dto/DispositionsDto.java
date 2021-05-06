package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.Dispositions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DispositionsDto {
    private Double value;
    private Date time;
    private String description;

    public DispositionsDto(Dispositions dispositions) {
        value = dispositions.getValue();
        time = dispositions.getTime();
        description = dispositions.getDescription();
    }
}
