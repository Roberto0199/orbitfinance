package com.orbitfinance.core.alert;

import com.orbitfinance.core.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertsEngineService {
    private final AlertRepository alertRepository;
    private final GeoIpService geoIpService;

    private static final BigDecimal CRITICAL_THRESHOLD = new BigDecimal("500.00");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a", java.util.Locale.ENGLISH);

    @Async
    public void processAlert(Transaction transaction) {

        try {
            Thread.sleep(500);

            String priority = transaction.getAmount()
                    .compareTo(CRITICAL_THRESHOLD) >= 0 ? "CRITICAL" : "NORMAL";

            String location = geoIpService.resolveLocation(transaction.getOriginIp());

            String formatteDate = transaction.getCreatedAt().format(FORMATTER);

            String message = String.format(
                    "Security Alert [%s]: Transfer of $%s from account %s on %s originating from %s",
                    priority,
                    transaction.getAmount(),
                    transaction.getSourceAccountNumber(),
                    formatteDate,
                    location

            );

            Alert alert = new Alert();
            alert.setTransactionId(transaction.getId());
            alert.setSourceAccountNumber(transaction.getSourceAccountNumber());
            alert.setAmount(transaction.getAmount());
            alert.setPriority(priority);
            alert.setOriginLocation(location);
            alert.setMessage(message);
            alertRepository.save(alert);

            log.info("{}", message);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Alert processing interrupted for transaction: {}", transaction.getId());
        }
    }

}
