package com.orbitfinance.core.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest  request) {
        Account account = accountService.createAccount(
                request.ownerName(),
                request.accountNumber(),
                request.initialBalance()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
    }
}
