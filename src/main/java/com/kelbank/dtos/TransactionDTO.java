package com.kelbank.dtos;

import java.math.BigDecimal;

public record TransactionDTO(Long receiverId, Long senderId, BigDecimal amount) {
}
