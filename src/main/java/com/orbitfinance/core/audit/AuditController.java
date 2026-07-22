package com.orbitfinance.core.audit;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountAuditResponse> getAccountAudit(@PathVariable String accountNumber){
        return ResponseEntity.ok(auditService.getAuditTrail(accountNumber));
    }
}

