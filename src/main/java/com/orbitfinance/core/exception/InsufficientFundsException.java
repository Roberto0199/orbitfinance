package com.orbitfinance.core.exception;

import com.orbitfinance.core.account.Account;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException{

    public InsufficientFundsException(String accountNumber, BigDecimal balance, BigDecimal amount){
      super(String.format(
              "Account %s has insufficient funds. Available: $%s, Requested: $%s",
              accountNumber, balance, amount
      ));
    }
}
