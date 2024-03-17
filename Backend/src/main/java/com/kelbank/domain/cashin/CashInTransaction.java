package com.kelbank.domain.cashin;

import com.kelbank.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "cashInTransaction")
@Table(name = "cashInTransaction")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CashInTransaction {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private String id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private CashInMethods cashInMethods;
}
