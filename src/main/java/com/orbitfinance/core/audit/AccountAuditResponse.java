package com.orbitfinance.core.audit;

import com.orbitfinance.core.account.Account;
import com.orbitfinance.core.alert.Alert;
import com.orbitfinance.core.transaction.Transaction;

import java.util.List;

public record AccountAuditResponse(
    Account account,
    List<Transaction> sentTransaction,
    List<Transaction> receivedTransaction,
    List<Alert> alerts
) {}

