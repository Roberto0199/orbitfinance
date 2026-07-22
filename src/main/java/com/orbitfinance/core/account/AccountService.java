package com.orbitfinance.core.account;

import com.orbitfinance.core.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createAccount(String ownerName, String accountNumber, BigDecimal initialBalance) {
        Account account = new Account();
        account.setOwnerName(ownerName);
        account.setAccountNumber(accountNumber);
        account.setBalance(initialBalance);
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }
}

