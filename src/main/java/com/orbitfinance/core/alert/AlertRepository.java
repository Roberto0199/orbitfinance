package com.orbitfinance.core.alert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 public interface AlertRepository extends JpaRepository<Alert, String>{

        List<Alert> findByPriority(String  priority);

        List<Alert> findBySourceAccountNumber(String accountNumber);
    }

