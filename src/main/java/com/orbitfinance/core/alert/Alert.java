package com.orbitfinance.core.alert;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "alerts")

public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String sourceAccountNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String originLocation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 500)
    private String message;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
