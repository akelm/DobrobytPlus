package com.example.dobrobytplus.dto;

import com.example.dobrobytplus.model.Operation;
import com.example.dobrobytplus.model.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
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