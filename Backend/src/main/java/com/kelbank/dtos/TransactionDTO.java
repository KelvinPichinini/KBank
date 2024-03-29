package com.kelbank.dtos;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public record TransactionDTO(BigDecimal amount, String receiverEmail) {
}
