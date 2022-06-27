package com.imorochi.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferDto {
    private Long originAccountNumber;
    private Long destinationAccountNumber;
    private Long bankId;
    private BigDecimal amount;
}
