package com.orbitfinance.core.account;

import java.math.BigDecimal;

public record CreateAccountRequest (
    String ownerName,
    String accountNumber,
    BigDecimal initialBalance
){}
