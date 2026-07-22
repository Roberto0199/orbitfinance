package com.orbitfinance.core.transaction;

import java.math.BigDecimal;

public record TransferRequest (
    String sourceAccountNumber,
    String targetAccountNumber,
    BigDecimal amount,
    String originIp
) {}