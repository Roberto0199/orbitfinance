package com.orbitfinance.core.transaction;


import com.orbitfinance.core.account.Account;
import com.orbitfinance.core.account.AccountRepository;
import com.orbitfinance.core.alert.AlertsEngineService;
import com.orbitfinance.core.exception.AccountNotFoundException;
import com.orbitfinance.core.exception.InsufficientFundsException;
import com.orbitfinance.core.exception.InvalidTransactionException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AlertsEngineService alertsEngineService;

    @Transactional
    public Transaction transfer(TransferRequest request) {

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Transfer amount must be greater than zero");
        }
        Account source = accountRepository.findByAccountNumber(request.sourceAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Source account number not found"));

        Account target = accountRepository.findByAccountNumber(request.targetAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Target account number not found"));

        if (source.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientFundsException(
                    source.getAccountNumber(),
                    source.getBalance(),
                    request.amount()
            );
        }

        source.setBalance(source.getBalance().subtract(request.amount()));
        target.setBalance(target.getBalance().add(request.amount()));

        accountRepository.save(source);
        accountRepository.save(target);

        Transaction transaction = new Transaction();
        transaction.setSourceAccountNumber(request.sourceAccountNumber());
        transaction.setTargetAccountNumber(request.targetAccountNumber());
        transaction.setAmount(request.amount());
        transaction.setStatus("SUCCESS");
        transaction.setOriginIp(request.originIp());

        Transaction saved = transactionRepository.save(transaction);

        alertsEngineService.processAlert(saved);

        return saved;
    }

    public List<Transaction> getAllTransactions(){

        return transactionRepository.findAll();
    }
}
