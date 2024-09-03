package com.witty.namij.finance.controllers;

import com.witty.namij.finance.TransactionReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Map<String, Long>> getTransactionCountByType(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getTransactionCountByType(startDate, endDate));
    }

    @GetMapping("/daily-transaction-volume")
    public ResponseEntity<Map<LocalDate, BigDecimal>> getDailyTransactionVolume(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getDailyTransactionVolume(startDate, endDate));
    }

    @GetMapping("/top-senders")
    public ResponseEntity<List<Map.Entry<String, BigDecimal>>> getTopSenders(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getTopSenders(startDate, endDate));
    }

    @GetMapping("/transaction-distribution-by-category")
    public ResponseEntity<Map<String, Long>> getTransactionDistributionByCategory(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getTransactionDistributionByCategory(startDate, endDate));
    }

    @GetMapping("/monthly-average-transaction-amount")
    public ResponseEntity<Map<String, BigDecimal>> getMonthlyAverageTransactionAmount(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getMonthlyAverageTransactionAmount(startDate, endDate));
    }
}