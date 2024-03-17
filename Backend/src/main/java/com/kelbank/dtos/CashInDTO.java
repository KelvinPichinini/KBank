package com.kelbank.dtos;

import com.kelbank.domain.cashin.CashInMethods;

import java.math.BigDecimal;

public record CashInDTO (BigDecimal amount, CashInMethods method) {
}
