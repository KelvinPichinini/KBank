package com.kelbank.dtos;

import com.kelbank.domain.cashin.CashInMethods;

import java.math.BigDecimal;

public record CashInDTO (Long receiverId, BigDecimal amount, CashInMethods method) {
}
