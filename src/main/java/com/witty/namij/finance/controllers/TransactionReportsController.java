package com.witty.namij.finance.controllers;

import com.witty.namij.finance.TransactionReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class TransactionReportsController {

    @Autowired
    private TransactionReportsService reportService;

    @GetMapping("/transaction-count-by-type")
    public ResponseEntity<Map<String, Long>> getTransactionCountByType() {
        return ResponseEntity.ok(reportService.getTransactionCountByType());
    }

    @GetMapping("/daily-transaction-volume")
    public ResponseEntity<Map<LocalDate, BigDecimal>> getDailyTransactionVolume() {
        return ResponseEntity.ok(reportService.getDailyTransactionVolume());
    }

    @GetMapping("/top-senders")
    public ResponseEntity<List<Map.Entry<String, BigDecimal>>> getTopSenders() {
        return ResponseEntity.ok(reportService.getTopSenders());
    }

    @GetMapping("/transaction-distribution-by-category")
    public ResponseEntity<Map<String, Long>> getTransactionDistributionByCategory() {
        return ResponseEntity.ok(reportService.getTransactionDistributionByCategory());
    }

    @GetMapping("/monthly-average-transaction-amount")
    public ResponseEntity<Map<String, BigDecimal>> getMonthlyAverageTransactionAmount() {
        return ResponseEntity.ok(reportService.getMonthlyAverageTransactionAmount());
    }
}