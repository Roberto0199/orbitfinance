package com.orbitfinance.core.audit;

import com.orbitfinance.core.account.Account;
import com.orbitfinance.core.account.AccountRepository;
import com.orbitfinance.core.alert.Alert;
import com.orbitfinance.core.alert.AlertRepository;
import com.orbitfinance.core.exception.AccountNotFoundException;
import com.orbitfinance.core.transaction.Transaction;
import com.orbitfinance.core.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AlertRepository alertRepository;

    public AccountAuditResponse getAuditTrail(String accountNumber){

        Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(()-> new AccountNotFoundException(accountNumber));

        List<Transaction> sent = transactionRepository.findBySourceAccountNumber(accountNumber);
        List<Transaction> received = transactionRepository.findByTargetAccountNumber(accountNumber);
        List<com.orbitfinance.core.alert.Alert> alerts =
                alertRepository.findBySourceAccountNumber(accountNumber);

        return new AccountAuditResponse(account, sent, received, alerts);

    }
}
