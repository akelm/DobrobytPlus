package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDto {
    private Double amount;
    private Date time;

    public  OperationDto(Operation operation) {
        this.amount=operation.getAmount();
        this.time=operation.getTime();
    }

}